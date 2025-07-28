package com.example.unibookapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.unibookapp.data.Review
import com.example.unibookapp.data.ReviewDao
import com.example.unibookapp.data.User
import com.example.unibookapp.data.UserBook
import com.example.unibookapp.data.UserBookDao
import com.example.unibookapp.data.UserDao
import com.example.unibookapp.ui.theme.UniBookAppTheme
import com.example.unibookapp.viewmodel.UserViewModel
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun testLoginFlow() {
        composeTestRule.setContent {
            UniBookAppTheme {
                LoginScreen(
                    // Simulate login check without real database
                    userDao = object : UserDao {
                        override suspend fun authenticate(username: String, password: String): User? {
                            return null // Always return null
                        }
                        override suspend fun insert(user: User) {
                            // Do nothing
                        }
                        override suspend fun getUsername(username: String): User? {
                            return null
                        }
                    },
                    userViewModel = UserViewModel(), // Construct UserViewModel
                    onSignupClick = { /* Do nothing */ },
                    onLoginSuccess = { /* Do nothing */ }
                )
            }
        }

        // Interact with UI elements using text labels
        composeTestRule.onNodeWithText("Username").performTextInput("TestUser")
        composeTestRule.onNodeWithText("Password").performTextInput("Qwerty123")
        composeTestRule.onNodeWithText("Log in").performClick()

    }


    @Test
    fun testSignupFlow() {
        composeTestRule.setContent {
            UniBookAppTheme {
                SignupScreen(
                    userDao = object : UserDao {
                        override suspend fun authenticate(username: String, password: String): User? {
                            return null // Not used in signup
                        }
                        override suspend fun insert(user: User) {
                            // Do nothing
                        }
                        override suspend fun getUsername(username: String): User? {
                            return null
                        }
                    },
                    onLoginClick = { /* Do nothing */ }
                )
            }
        }
        composeTestRule.onNodeWithText("Username").performTextInput("NewUser123")
        composeTestRule.onNodeWithText("Password").performTextInput("Pass123")
        composeTestRule.onAllNodesWithText("Sign Up")[1].performClick()
    }

    @Test
    fun testProfileScreenDisplaysStatistics() {
        composeTestRule.setContent {
            UniBookAppTheme {
                // Create fake Dao's for test
                val mockUserBookDao = object : UserBookDao {
                    override suspend fun insert(userBook: UserBook): Long = 0
                    override suspend fun getBooksByUser(username: String): List<UserBook> = emptyList()
                    override suspend fun updateReadingStatus(username: String, bookId: String, status: String) {}
                    override suspend fun delete(userBook: UserBook) {}
                }
                val mockReviewDao = object : ReviewDao {
                    override suspend fun insert(review: Review): Long = 0
                    override suspend fun getReviewByUserAndBook(bookId: String, username: String): Review? = null
                }
                ProfileScreen(
                    username = "testUser",
                    userBookDao = mockUserBookDao,
                    reviewDao = mockReviewDao
                )
            }
        }
        composeTestRule.onNodeWithText("Reading Statistics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Books read: 0").assertIsDisplayed()
    }
}
