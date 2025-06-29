package com.example.unibookapp

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.unibookapp.viewmodel.UserViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.setValue


enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    DASHBOARD("dashboard", "Dashboard", Icons.Default.Home, "Dashboard")
}


@Composable
fun BottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedDestination by rememberSaveable { mutableIntStateOf(Destination.DASHBOARD.ordinal) }

    NavigationBar {
        Destination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedDestination == index,
                onClick = {
                    navController.navigate(route = destination.route)
                    selectedDestination = index
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = destination.contentDescription
                    )
                },
                label = { Text(destination.label) }
            )
        }
    }
}