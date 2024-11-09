package com.example.dotsandboxes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun SinglePlayerPage(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            Row() {
                StartPageButton(emojiUnicode = "\uD83C\uDFE0", text = "Home", onClick = {
                    navController.navigate("StartPage")
                })
                StartPageButton(emojiUnicode = "⚙\uFE0F", text = "Info", onClick = {
                    navController.navigate("InfoPage")
                })
            }
            Text("This is the SinglePalyerPage", color = Color.White)
        }
    }
}
