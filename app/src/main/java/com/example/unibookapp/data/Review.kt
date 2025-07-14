package com.example.unibookapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val reviewId: Long = 0,
    val username: String,
    val bookId: String,
    val rating: Float,
    val reviewtext: String? = null, // Optional
    val datePosted: Long = System.currentTimeMillis()
)
