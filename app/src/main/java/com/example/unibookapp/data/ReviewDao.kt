package com.example.unibookapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: Review): Long

    @Query("SELECT * FROM reviews WHERE bookId = :bookId AND username = :username LIMIT 1")
    suspend fun getReviewByUserAndBook(bookId: String, username: String): Review?
}