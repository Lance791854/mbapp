package com.example.unibookapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.unibookapp.data.Book
import com.example.unibookapp.data.BookDao
import com.example.unibookapp.data.UserBookDao
import kotlinx.coroutines.launch
import com.example.unibookapp.data.UserBook
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState




@Composable
fun BookDetailScreen(
    bookId: String,
    bookDao: BookDao,
    userBookDao: UserBookDao,
    username: String,
    modifier: Modifier = Modifier
) {
    var book by remember { mutableStateOf<Book?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(bookId) {
        coroutineScope.launch {
            book = bookDao.getBookById(bookId)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            book?.let { myBook ->
                AsyncImage(
                    model = myBook.coverUrl,
                    contentDescription = "Book Cover",
                    modifier = Modifier
                        .size(180.dp, 240.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.landscape_placeholder),
                    error = painterResource(R.drawable.landscape_placeholder)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = myBook.title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Author: ${myBook.author}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val userBookToDelete =
                                UserBook(username = username, bookId = myBook.bookId)
                            userBookDao.delete(userBookToDelete)
                            snackbarHostState.showSnackbar("${myBook.title} removed from library")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Remove Book")
                }


                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Description:",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = if (expanded) "Collapse" else "Expand"
                        )
                    }
                    Text(
                        text = myBook.description ?: "No description available.",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (expanded) Int.MAX_VALUE else 2,
                        overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}




