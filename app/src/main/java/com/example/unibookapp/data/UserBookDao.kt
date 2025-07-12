package com.example.unibookapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userBook: UserBook): Long

    @Query("SELECT * FROM user_books WHERE username = :username")
    suspend fun getBooksByUser(username: String): List<UserBook>

    @Delete
    suspend fun delete(userBook: UserBook)
}