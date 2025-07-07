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

    // Main layout for screen
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
                    Column {
                        Text(text = bookItem.volumeInfo.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = bookItem.volumeInfo.authors?.joinToString() ?: "Unknown Author")
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch {
                            // Convert BookItem to work in book database
                            val book = Book(
                                bookId = bookItem.id,
                                title = bookItem.volumeInfo.title,
                                author = bookItem.volumeInfo.authors?.firstOrNull()
                                    ?: "Unknown Author",
                                description = null,
                                coverUrl = null
                            )
                            // Save to database
                            bookDao.insert(book)
                            // Link book to user
                            userBookDao.insert(UserBook(username = username, bookId = bookItem.id))
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}
