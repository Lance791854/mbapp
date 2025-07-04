package com.example.unibookapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.unibookapp.data.User
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
}
