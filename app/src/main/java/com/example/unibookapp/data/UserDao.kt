package com.example.unibookapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) // skip insert if conflict occurs
    suspend fun insert(user: User)

    @Query("Select * FROM User WHERE username = :username AND password = :password")
    suspend fun authenticate(username: String, password: String): User?

    @Query("SELECT * FROM User WHERE username = :username")
    suspend fun getUsername(username: String): User?
}