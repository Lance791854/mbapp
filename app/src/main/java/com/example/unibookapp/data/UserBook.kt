package com.example.unibookapp.data

import androidx.room.Entity

@Entity(
    tableName = "user_books",
    primaryKeys = ["username", "bookId"]
)

data class UserBook(
    val username: String,
    val bookId: String,
    val datePosted: Long = System.currentTimeMillis(), // Might be useful to determine how long a book took to read
    val readingStatus: String = "want_to_read", // Default want to read
    val currentPage: Int = 0 // Current page the user is up to in the book, default 0
)