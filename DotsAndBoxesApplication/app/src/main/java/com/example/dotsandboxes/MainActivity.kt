package com.example.dotsandboxes

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dotsandboxes.ui.theme.DotsAndBoxesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DotsAndBoxesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val model: GameStateViewModel = viewModel()
                    NavHost(navController = navController, startDestination = "StartPage") {
                        composable("StartPage") {
                            StartPage(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                            )
                        }
                        composable("InfoPage") {
                            InfoPage(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("MultiPlayerPage") {
                            model.createPlayerForMultiPlayer()
                            MultiPlayerPage(
                                modifier = Modifier,
                                navController = navController,
                                model = model
                            )
                        }
                        composable("SinglePlayerPage") {
                            model.createPlayerForSinglePlayer()
                            SinglePlayerPage(
                                modifier = Modifier,
                                navController = navController,
                                model = model
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun StartPage(modifier: Modifier = Modifier, navController: NavController) {

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
            Row() {
                Text(
                    text = "Dots And Boxes",
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row() {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                ) {
                    Spacer(modifier = modifier)
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(2f)
                ) {
                    GameButton(
                        text = "Single Player",
                        modifier = modifier,
                        onClick = {
                            navController.navigate("SinglePlayerPage")
                        })
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                ) {
                    Spacer(modifier = modifier)
                }

            }
            Row() {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                ) {
                    Spacer(modifier = modifier)
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(2f)
                ) {
                    GameButton(
                        text = "Multiplayer",
                        modifier = modifier,
                        onClick = {
                            navController.navigate("MultiPlayerPage")
                        })
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .weight(1f)
                ) {
                    Spacer(modifier = modifier)
                }
            }
            Row() {
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


@Composable
fun GameButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Text(text, fontSize = 25.sp)
    }
}


