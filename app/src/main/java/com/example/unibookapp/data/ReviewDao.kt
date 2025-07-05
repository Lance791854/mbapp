package com.example.unibookapp.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ReviewDao {
    @Insert
    suspend fun insert(review: Review): Long

    // Need an update query
}