package com.example.unibookapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<String?>(null)
    val currentUser: StateFlow<String?> = _currentUser

    fun login(username: String) {
        _currentUser.value = username
    }

    fun logout() {
        _currentUser.value = null
    }
}