package com.example.dotsandboxes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SinglePlayerPage(modifier: Modifier, navController: NavController) {
    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StartPageButton(emojiUnicode = "\uD83C\uDFE0", text = "Home", onClick = {
                    navController.navigate("StartPage")
                })
                Text("Player 1", color = Color.White)
                Text("1", color = Color.White)
            }
            Column(
                modifier = modifier
                    .weight(4f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Game Area", color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StartPageButton(emojiUnicode = "⚙\uFE0F", text = "Info", onClick = {
                    navController.navigate("InfoPage")
                })
                Text("Player 2", color = Color.White)
                Text("2", color = Color.White)
            }
        }
    }
}
