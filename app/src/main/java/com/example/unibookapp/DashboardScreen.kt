package com.example.unibookapp

import androidx.compose.foundation.BorderStroke
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
import com.example.unibookapp.data.UserBook
import com.example.unibookapp.data.UserBookDao


@Composable
fun DashboardScreen(
    username: String,
    userBookDao: UserBookDao,
    modifier: Modifier = Modifier
) {

    var userBooks by remember { mutableStateOf<List<UserBook>>(emptyList()) }

    LaunchedEffect(username) {
        userBooks = userBookDao.getBooksByUser(username)
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

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Books read = $booksRead", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Currently reading = $reading",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Want to read = $wantToRead",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total books = $totalBooks",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


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
    }
}



