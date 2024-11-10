package com.example.dotsandboxes

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InfoPage(modifier: Modifier = Modifier, navController: NavController) {
    val activity = LocalContext.current as? Activity
    DisposableEffect(Unit) {
        val originalOrientation = activity?.requestedOrientation

        // Set the screen orientation to landscape
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // When the composable leaves the composition, restore the original orientation
        onDispose {
            activity?.requestedOrientation = originalOrientation
                ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier
                .matchParentSize()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Spacer(modifier)
            }
            Row {
                Text(
                    "Hier könnte Ihre Werbung stehen",
                    color = Color.White
                )
            }

            Row {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    StartPageButton(
                        emojiUnicode = "\uD83C\uDFE0",
                        text = "Home",
                        onClick = {
                            navController.navigate("StartPage")
                        },
                        Arrangement.Bottom
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                ) {
                    Spacer(modifier = modifier)
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    StartPageButton(
                        emojiUnicode = "⚙\uFE0F",
                        text = "Info",
                        onClick = {
                            navController.navigate("InfoPage")
                        },
                        Arrangement.Bottom
                    )
                }
            }
        }
    }
}
