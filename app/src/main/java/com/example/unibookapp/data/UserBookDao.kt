package com.example.unibookapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userBook: UserBook): Long
}