package com.example.unibookapp

import com.example.unibookapp.viewmodel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test


class UserViewModelTest {
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        // Initialize the ViewModel before each test
        viewModel = UserViewModel()
    }

    @Test
    fun loginWithUsernameUpdatesCurrentUser() {
        // Set and log in with username
        val testUsername = "testUser"
        viewModel.login(testUsername)

        // Then the currentUser state should be testUsername
        val result = viewModel.currentUser.value
        // Throws an error if both objects are not equal
        assertEquals(testUsername, result)
    }

    @Test
    fun logoutClearsCurrentUser() {
        // Log a user in
        viewModel.login("testUser")

        // Call logout method
        viewModel.logout()

        // Then the currentUser state should be null
        val result = viewModel.currentUser.value
        assertNull(result)
    }
}
