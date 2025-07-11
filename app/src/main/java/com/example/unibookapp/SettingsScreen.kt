package com.example.unibookapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unibookapp.ui.theme.UniBookAppTheme


@Composable
fun SettingsScreen(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "SettingsScreen")
        Text(
            text = "Logout",
            color = Color.Blue,
            modifier = Modifier.clickable { onLogoutClick() }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    UniBookAppTheme {
//        SettingsScreen()
    }
}