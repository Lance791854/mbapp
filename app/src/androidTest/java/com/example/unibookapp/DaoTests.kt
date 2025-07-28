package com.example.unibookapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.unibookapp.data.Book
import com.example.unibookapp.data.BookDao
import com.example.unibookapp.data.Review
import com.example.unibookapp.data.ReviewDao
import com.example.unibookapp.data.UniBookDatabase
import com.example.unibookapp.data.User
import com.example.unibookapp.data.UserBook
import com.example.unibookapp.data.UserBookDao
import com.example.unibookapp.data.UserDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DaoTests {
    private lateinit var database: UniBookDatabase
    private lateinit var userDao: UserDao
    private lateinit var bookDao: BookDao
    private lateinit var userBookDao: UserBookDao
    private lateinit var reviewDao: ReviewDao



    @Before
    fun setUp() {
        // Create a temporary in memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UniBookDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
        bookDao = database.bookDao()
        userBookDao = database.userBookDao()
        reviewDao = database.reviewDao()
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

    @Test
    fun addBookAndReview() {
        runBlocking {
            val testUser = User("BookReviewer", "Pass123")
            val testBook = Book("test-id", "Test Book", "Author", "2023")
            val testUserBook = UserBook("BookReviewer", "test-id", System.currentTimeMillis(), "read", 0)
            val testReview = Review(0, "BookReviewer", "test-id", 5.0f, "Great book!", System.currentTimeMillis())

            userDao.insert(testUser)
            bookDao.insert(testBook)
            userBookDao.insert(testUserBook)
            reviewDao.insert(testReview)

            val userReview = reviewDao.getReviewByUserAndBook("test-id", "BookReviewer")
            Assert.assertNotNull(userReview)
            Assert.assertEquals(5.0f, userReview?.rating)
        }
    }
}

