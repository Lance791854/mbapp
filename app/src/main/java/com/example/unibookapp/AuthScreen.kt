package com.example.unibookapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unibookapp.ui.theme.UniBookAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unibookapp.data.BookDao
import com.example.unibookapp.data.User
import com.example.unibookapp.data.UserBookDao
import com.example.unibookapp.data.UserDao
import kotlinx.coroutines.launch
import com.example.unibookapp.viewmodel.UserViewModel



@Composable
fun AuthScreen(
    userDao: UserDao,
    bookDao: BookDao,
    userBookDao: UserBookDao,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentUser by userViewModel.currentUser.collectAsState()
    val startDestination = if (currentUser != null) Destination.DASHBOARD.route else "login"


    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentUser != null) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    userDao = userDao,
                    userViewModel = userViewModel,
                    onSignupClick = { navController.navigate("signup") },
                    onLoginSuccess = { navController.navigate(Destination.DASHBOARD.route)}
                )
            }
            composable("signup") {
                SignupScreen(
                    userDao = userDao,
                    onLoginClick = { navController.navigate("login") }
                )
            }
            composable(Destination.DASHBOARD.route) {
                currentUser?. let { username ->
                    DashboardScreen(
                        username = username
                    )
                }
            }
            composable(Destination.LIBRARY.route) {
                currentUser?. let { username ->
                    LibraryScreen(
                        bookDao = bookDao,
                        userBookDao = userBookDao,
                        username = username,
                        onBookClick = { bookId -> navController.navigate("bookDetail/$bookId") }
                    )
                }
            }
            composable("bookDetail/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId")
                if (bookId != null) {
                    BookDetailScreen(
                        bookId = bookId,
                        bookDao = bookDao,
                        userBookDao = userBookDao
                    )
                }
            }
            composable(Destination.SEARCH.route) {
                currentUser?. let { username ->
                    BookSearchScreen(
                        bookDao = bookDao,
                        userBookDao = userBookDao,
                        username = username
                    )
                }
            }
            composable(Destination.PROFILE.route) {
                ProfileScreen()
            }
            composable(Destination.SETTINGS.route) {
                SettingsScreen(
                    onLogoutClick = {
                        userViewModel.logout()
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}

@Composable
fun BookDetailScreen(bookId: String, bookDao: BookDao, userBookDao: UserBookDao) {
    TODO("Not yet implemented")
}


@Composable
fun LoginScreen(
    userDao: UserDao,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    onSignupClick: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var username by remember { mutableStateOf(TextFieldValue("")) } // Remembers the username
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it }, // Update text field for each new input
            label = { Text("Username") },
            singleLine = true
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation() // Hides password when entering

        )

        Text(
            text = "Log in",
            color = Color.Blue,
            modifier = Modifier.clickable {
                coroutineScope.launch {
                    val user = userDao.authenticate(username.text, password.text)
                    if (user != null) {
                        println("Login successful!")
                        userViewModel.login(username.text)
                        onLoginSuccess(username.text)
                    } else {
                        println("Invalid username or password")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Don't have an account? ")
            Text(
                text = "Sign Up",
                color = Color.Blue,
                modifier = Modifier.clickable { onSignupClick() }
            )
        }
    }
}

@Composable
fun SignupScreen(
    userDao: UserDao,
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue("")) } // Remembers the username
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it }, // Update text field for each new input
            label = { Text("Username") },
            singleLine = true
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation() // Hides password when entering

        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        Text(
            text = "Sign Up",
            color = Color.Blue,
            modifier = Modifier.clickable {
                if (username.text.isEmpty() || password.text.isEmpty()) {
                    errorMessage = "Username and password are required"
                    // Disabled for development
//                } else if (password.text.length < 8) {
//                    errorMessage = "Password must be at least 8 characters long"
                } else {
                    coroutineScope.launch {
                        val existingUser = userDao.getUsername(username.text)
                        if (existingUser != null) {
                            errorMessage = "Username already exists."
                        } else {
                            errorMessage = ""
                            userDao.insert(User(username.text, password.text))
                            onLoginClick()
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Already have an account? ")
            Text(
                text = "Log in",
                color = Color.Blue,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    UniBookAppTheme {
//        AuthScreen()
    }
}