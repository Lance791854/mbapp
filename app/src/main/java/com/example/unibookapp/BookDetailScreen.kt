package com.example.unibookapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.unibookapp.data.BookDao
import com.example.unibookapp.data.UserBookDao
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.example.unibookapp.data.Book
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text

@Composable
fun BookDetailScreen(
    bookId: String,
    bookDao: BookDao,
    userBookDao: UserBookDao,
    modifier: Modifier = Modifier
) {
    var book by remember { mutableStateOf<Book?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(bookId) {
        coroutineScope.launch {
            book = bookDao.getBookById(bookId)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        book?.let { myBook ->
            Text(text = myBook.title)
        }
    }
}




