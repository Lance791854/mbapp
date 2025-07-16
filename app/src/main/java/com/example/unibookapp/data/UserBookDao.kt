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

    @Query("UPDATE user_books SET readingStatus = :status WHERE username = :username AND bookId = :bookId")
    suspend fun updateReadingStatus(username: String, bookId: String, status: String)

    @Delete
    suspend fun delete(userBook: UserBook)
}