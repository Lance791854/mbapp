package com.example.unibookapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun SettingsScreen(
    isDarkMode: Boolean?,
    onThemeChange: (Boolean?) -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isRatingsPublic by remember { mutableStateOf(false) }
    var isReviewsPublic by remember { mutableStateOf(false) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.displaySmall)


        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Display ratings publicly",
                    style = MaterialTheme.typography.titleMedium
                )

                Switch(
                    checked = isRatingsPublic,
                    onCheckedChange = { isRatingsPublic = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Display reviews publicly",
                    style = MaterialTheme.typography.titleMedium
                )

                Switch(
                    checked = isReviewsPublic,
                    onCheckedChange = { isReviewsPublic = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))



        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.titleMedium
                )

                Switch(
                    checked = isDarkMode == true,
                    onCheckedChange = { onThemeChange(it) }
                )

            }
        }


        Spacer(modifier = Modifier.weight(1f)) // Push logout to bottom of page


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogoutClick() },
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Red
            )
        }
    }
}


