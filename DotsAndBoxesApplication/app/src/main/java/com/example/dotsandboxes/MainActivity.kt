package com.example.dotsandboxes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    NavHost(navController = navController, startDestination = "StartPage") {
                        composable("StartPage") {
                            StartPage(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("InfoPage") {
                            InfoPage(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("MultiPlayerPage") {
                            MultiPlayerPage(
                                modifier = Modifier,
                                navController = navController
                            )
                        }
                        composable("SinglePlayerPage") {
                            SinglePlayerPage(
                                modifier = Modifier,
                                navController = navController
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier.matchParentSize()) {
            Text(
                text = "Dots And Boxes",
                color = Color.White
            )
            GameButton(text = "Single Player", onClick = {
                navController.navigate("SinglePlayerPage")
            })
            GameButton(text = "Multiplayer", onClick = {
                navController.navigate("MultiPlayerPage")
            })
            Row() {
                StartPageButton(
                    emojiUnicode = "\uD83C\uDFE0",
                    text = "Home",
                    onClick = {
                        navController.navigate("StartPage")
                    },
                    Arrangement.Bottom
                )
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


@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text)
    }
}

@Composable
fun StartPageButton(
    emojiUnicode: String,
    text: String,
    onClick: () -> Unit,
    verticalArrangement: Arrangement.Vertical
) {
    TextButton(
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = verticalArrangement
        ) {
            Text(emojiUnicode, fontSize = 40.sp)
            Text(text, fontSize = 20.sp)
        }
    }
}

