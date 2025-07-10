package com.example.unibookapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unibookapp.data.Book
import com.example.unibookapp.data.BookDao
import com.example.unibookapp.data.UserBookDao
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextField
import androidx.compose.runtime.derivedStateOf


@Composable
fun LibraryScreen(
    bookDao: BookDao,
    userBookDao: UserBookDao,
    username: String,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var userBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(username) {
        coroutineScope.launch {
            val userBookEntries = userBookDao.getBooksByUser(username)
            val bookIds = userBookEntries.map { it.bookId }
            userBooks = bookDao.getBooksByIds(bookIds)
        }
    }


    val filteredBooks by remember(userBooks, searchQuery) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                userBooks
            } else {
                userBooks.filter { book ->
                    book.title.contains(searchQuery, ignoreCase = true) ||
                            book.author.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Text(text = "My Books")
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))



        if (filteredBooks.isEmpty() && searchQuery.isBlank()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No books here yet!")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Visit the 'Search' tab to add books to your library.")
            }
        } else if (filteredBooks.isEmpty() && searchQuery.isNotBlank()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No matches found for your search.")
            }
        } else {
            LazyColumn {
                items(filteredBooks) { book ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { onBookClick(book.bookId) }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {

                            AsyncImage(
                                model = book.coverUrl,
                                contentDescription = "Book Cover",
                                modifier = Modifier
                                    .size(90.dp, 120.dp),
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.landscape_placeholder),
                                error = painterResource(R.drawable.landscape_placeholder)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            ) {

                                Text(
                                    text = book.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = book.author,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

