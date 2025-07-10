package com.example.unibookapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book): Long

    @Query("SELECT * FROM books WHERE bookId IN (:bookIds)")
    suspend fun getBooksByIds(bookIds: List<String>): List<Book>

    @Query("SELECT * FROM books WHERE bookId = :bookId LIMIT 1")
    suspend fun getBookById(bookId: String): Book?
}