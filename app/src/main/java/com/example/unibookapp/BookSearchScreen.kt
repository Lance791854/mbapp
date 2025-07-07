package com.example.unibookapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unibookapp.data.*
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*

@Composable
fun BookSearchScreen(
    bookDao: BookDao, // Needed for saving books to db
    userBookDao: UserBookDao, // Needed for saving books to db
    username: String,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<BookItem>>(emptyList()) } // Holds list of books returned by api
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var addedBookIds by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(username) {
        coroutineScope.launch {
            // Fetch all UserBook entries for the current user
            val userBooks = userBookDao.getBooksByUser(username)
            addedBookIds = userBooks.map { it.bookId }.toSet()
        }
    }

    // Main layout for screen
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Text input field for users search query
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search for books") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        // Makes a asynchronous API call that to avoid blocking or overloading UI thread.
                        val response = RetrofitInstanceGoogle.api.searchBooks(searchQuery)
                        searchResults = response.body()?.items ?: emptyList()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(searchResults) { bookItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = bookItem.volumeInfo.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 2, // Max of 2 lines used when displaying
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis // Truncate if too long
                            )
                            Text(
                                text = bookItem.volumeInfo.authors?.joinToString()
                                    ?: "Unknown Author",
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                        if (addedBookIds.contains(bookItem.id)) {
                            Text(
                                text = "Added",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        } else {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        // Convert BookItem to work in book database
                                        val book = Book(
                                            bookId = bookItem.id,
                                            title = bookItem.volumeInfo.title,
                                            author = bookItem.volumeInfo.authors?.firstOrNull()
                                                ?: "Unknown Author",
                                            description = bookItem.volumeInfo.description,
                                            coverUrl = bookItem.volumeInfo.imageLinks?.thumbnail
                                        )
                                        // Save to database
                                        bookDao.insert(book)
                                        // Link book to user
                                        userBookDao.insert(
                                            UserBook(
                                                username = username,
                                                bookId = bookItem.id
                                            )
                                        )
                                        // Needed for remember if a book was added in memory
                                        addedBookIds = addedBookIds + bookItem.id
                                        // Show snackbar confirmation when adding
                                        snackbarHostState.showSnackbar("${book.title} added to library")
                                    }
                                },
                                modifier = Modifier.widthIn(min = 60.dp) // Set button width
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


