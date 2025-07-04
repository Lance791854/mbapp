package com.example.unibookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unibookapp.data.UniBookDatabase
import com.example.unibookapp.ui.theme.UniBookAppTheme
import com.example.unibookapp.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = UniBookDatabase.getDatabase(applicationContext)
        val userDao = db.userDao()

        enableEdgeToEdge()
        setContent {
            UniBookAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val userViewModel: UserViewModel = viewModel()
                    AuthScreen(
                        userDao = userDao,
                        userViewModel = userViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
