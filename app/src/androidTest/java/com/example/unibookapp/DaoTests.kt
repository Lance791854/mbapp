package com.example.unibookapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.unibookapp.data.UniBookDatabase
import com.example.unibookapp.data.User
import com.example.unibookapp.data.UserDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DaoTests {
    private lateinit var database: UniBookDatabase
    private lateinit var userDao: UserDao


    @Before
    fun setUp() {
        // Create a temporary in memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UniBookDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
    }

    // Shut down temp database when test completed
    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun addUserAndLogin() {
        runBlocking {
            // Test user credentials
            val testUser = User("BookLover55", "Qwerty123")
            // Insert test user into database
            userDao.insert(testUser)
            // Attempt to authenticate the user with the credentials
            val loggedInUser = userDao.authenticate("BookLover55", "Qwerty123")
            // Check that username matches logged in user
            Assert.assertEquals("BookLover55", loggedInUser?.username)
        }
    }
}


// For future tests insert with conflict, incorrect password and case sensitivity