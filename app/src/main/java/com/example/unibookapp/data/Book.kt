package com.example.unibookapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Store books
@Entity (tableName = "books")
data class Book(
    @PrimaryKey val bookId: String, // Unique ID for the book
    val title: String,
    val author: String,
    val description: String? = null, // Option fields in case they don't exist
    val coverUrl: String? = null
)