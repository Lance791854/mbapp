package com.example.unibookapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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




@Composable
fun LibraryScreen(
    bookDao: BookDao,
    userBookDao: UserBookDao,
    username: String,
    modifier: Modifier = Modifier
) {
    var userBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(username) {
        coroutineScope.launch {
            val userBookEntries = userBookDao.getBooksByUser(username)
            val bookIds = userBookEntries.map { it.bookId }
            userBooks = bookDao.getBooksByIds(bookIds)
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



        LazyColumn {
            items(userBooks) { book ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        AsyncImage(
                            model = book.coverUrl,
                            contentDescription = "image",
                            modifier = Modifier
                                .size(90.dp, 120.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.landscape_placeholder),

                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

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

