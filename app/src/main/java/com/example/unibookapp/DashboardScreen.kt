package com.example.unibookapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.unibookapp.data.Book
import com.example.unibookapp.data.BookDao
import com.example.unibookapp.data.UserBook
import com.example.unibookapp.data.UserBookDao
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage


@Composable
fun DashboardScreen(
    username: String,
    userBookDao: UserBookDao,
    bookDao: BookDao,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var userBooks by remember { mutableStateOf<List<UserBook>>(emptyList()) }
    var currentlyReadingBook by remember { mutableStateOf<Book?>(null) }

    LaunchedEffect(username) {
        userBooks = userBookDao.getBooksByUser(username)

        val readingBooks = userBooks.filter { it.readingStatus == "reading" }
        if (readingBooks.isNotEmpty()) {
            val randomReadingUserBook = readingBooks.random()
            currentlyReadingBook = bookDao.getBookById(randomReadingUserBook.bookId)
        }
    }

    val booksRead = userBooks.count { it.readingStatus == "read" }
    val reading = userBooks.count { it.readingStatus == "reading" }
    val wantToRead = userBooks.count { it.readingStatus == "want_to_read" }
    val totalBooks = booksRead + reading + wantToRead

    val readingTips = listOf(
        "Try setting a daily reading goal to build a consistent habit!",
        "Keep a reading journal to track your thoughts and favorite quotes.",
        "Join a book club to discover new genres and discuss with others.",
        "Try the 5-minute rule: read for just 5 minutes when you don't feel like it.",
        "Create a cozy reading nook in your favorite spot at home."
    )
    val currentTip = remember { readingTips.random() }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BookNest",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hello, $username!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            border = BorderStroke(1.dp, Color.Black)

        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "My activity", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(24.dp))

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Books read = $booksRead", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Books your current reading = $reading",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Books you want to read = $wantToRead",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total books = $totalBooks",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (totalBooks > 0) "Keep up the great work!" else "Start your reading journey!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Reading Tip", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = currentTip,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        currentlyReadingBook?.let { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable { onBookClick(book.bookId) },
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Currently Reading",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = book.coverUrl,
                            contentDescription = "Book Cover",
                            modifier = Modifier.size(60.dp, 80.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.landscape_placeholder),
                            error = painterResource(R.drawable.landscape_placeholder)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 2,
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



