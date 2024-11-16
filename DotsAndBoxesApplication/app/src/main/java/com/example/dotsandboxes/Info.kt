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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun InfoPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    model: GameStateViewModel
) {
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
                Text(
                    "How to Play the Game",
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = modifier)
            Row {
                Text(
                    "Try to gather as many points \nas possible by capturing boxes.",
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = modifier)

            Row {
                Text(
                    "Capture a box by closing it. \nTry it out:",
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.Top

            ) {
                model.rows = 3
                model.columns = 3
                model.createPlayerForSinglePlayer()
                model.buttonClicked(1, 1, true)
                model.currentPlayer = model.listOfPlayers[1]
                model.buttonClicked(1, 0, true)
                model.buttonClicked(1, 0, false)
                DotsAndBoxesScaffold(modifier, model, navController)
            }
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
                        model.resetGame()
                    },
                    Arrangement.Bottom
                )
            }

            //TODO: Add logic, that once the user captures a box, it gets confirmed
            // with something like "You got it. Now you're ready to play!"

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
                        model.resetGame()
                    },
                    Arrangement.Bottom
                )
            }
        }
    }
}
