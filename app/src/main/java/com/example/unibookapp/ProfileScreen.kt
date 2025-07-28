package com.example.unibookapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import com.example.unibookapp.data.ReviewDao
import com.example.unibookapp.data.UserBook
import com.example.unibookapp.data.UserBookDao

@Composable
fun ProfileScreen(
    username: String,
    userBookDao: UserBookDao,
    reviewDao: ReviewDao,
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

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Text(text = "Profile", style = MaterialTheme.typography.displaySmall)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(
                text = "@$username",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Reading Statistics", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Books read: $booksRead", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Currently reading: $reading",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Want to read: $wantToRead", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Total books: $totalBooks", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Account Info", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                val firstBookDate = userBooks.minByOrNull { it.datePosted }?.datePosted
                val daysSinceJoined = if (firstBookDate != null) {
                    ((System.currentTimeMillis() - firstBookDate) / (1000 * 60 * 60 * 24)).toInt()
                } else 0

                Text(text = "Member for: $daysSinceJoined days", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))




    }
}

