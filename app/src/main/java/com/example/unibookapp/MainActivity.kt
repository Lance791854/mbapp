package com.example.unibookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unibookapp.data.UniBookDatabase
import com.example.unibookapp.ui.theme.UniBookAppTheme
import com.example.unibookapp.viewmodel.ThemeViewModel
import com.example.unibookapp.viewmodel.UserViewModel
import androidx.compose.runtime.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = UniBookDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()
        val bookDao = database.bookDao()
        val userBookDao = database.userBookDao()
        val reviewDao = database.reviewDao()

        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()

            UniBookAppTheme(darkModeOverride = isDarkMode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val userViewModel: UserViewModel = viewModel()
                    AuthScreen(
                        userDao = userDao,
                        bookDao = bookDao,
                        userBookDao = userBookDao,
                        userViewModel = userViewModel,
                        reviewDao = reviewDao,
                        themeViewModel = themeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
