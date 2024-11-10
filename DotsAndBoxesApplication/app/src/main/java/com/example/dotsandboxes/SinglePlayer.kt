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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SinglePlayerPage(modifier: Modifier, navController: NavController, model: GameStateViewModel) {

    val activity = LocalContext.current as? Activity
    DisposableEffect(Unit) {
        val originalOrientation = activity?.requestedOrientation

        // Set the screen orientation to landscape
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // When the composable leaves the composition, restore the original orientation
        onDispose {
            activity?.requestedOrientation = originalOrientation
                ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            // Player 1
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Row(
                    modifier
                        .fillMaxHeight()
                        .weight(1.5f)
                        .padding(top = 50.dp),
                    verticalAlignment = Alignment.Top

                ) {
                    StartPageButton(
                        emojiUnicode = "\uD83C\uDFE0",
                        text = "Home",
                        onClick = {
                            navController.navigate("StartPage")
                            model.resetGame()
                        },
                        Arrangement.Top
                    )
                }
                Row(
                    modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            model.listOfPlayers[0].name,
                            color = model.listOfPlayers[0].playerColor.value,
                            fontSize = 20.sp
                        )
                        Spacer(modifier.heightIn(15.dp))
                        Text(
                            model.listOfPlayers[0].numberOfFieldsWon.value.toString(),
                            color = model.listOfPlayers[0].playerColor.value,
                            fontSize = 20.sp
                        )
                    }
                }
            }
            // Game Column
            Column(
                modifier = modifier
                    .weight(2.5f)
                    .fillMaxHeight()
                    .padding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DotsAndBoxesScaffold(modifier, model, navController)
            }

            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Row(
                    modifier
                        .fillMaxHeight()
                        .weight(1.5f)
                        .padding(top = 50.dp),
                    verticalAlignment = Alignment.Top

                ) {
                    StartPageButton(
                        emojiUnicode = "⚙\uFE0F",
                        text = "Info",
                        onClick = {
                            navController.navigate("InfoPage")
                            model.resetGame()
                        },
                        Arrangement.Top
                    )
                }
                Row(
                    modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            model.listOfPlayers[1].name,
                            color = model.listOfPlayers[1].playerColor.value,
                            fontSize = 20.sp
                        )
                        Spacer(modifier.heightIn(15.dp))
                        Text(
                            model.listOfPlayers[1].numberOfFieldsWon.value.toString(),
                            color = model.listOfPlayers[1].playerColor.value,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}



