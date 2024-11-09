package com.example.dotsandboxes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
                DotsAndBoxesScaffold(modifier)
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


@Composable
fun DotsAndBoxesGameBoard(columns: Int, rows: Int, modifier: Modifier) {
    val dotRadius = 8f
    val spacing = 100f

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(color = Color.Black)
    ) {
        // Draw dots
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                val x = i * spacing + spacing
                val y = j * spacing + spacing
                drawCircle(
                    color = Color.White,
                    radius = dotRadius,
                    center = Offset(x, y)
                )
            }
        }
    }

//    // Placeholder for lines, can detect taps here for interaction
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { offset ->
//                    // Handle line drawing based on tap position
//                }
//            }
//    )
}

@Composable
fun GameScreen(modifier: Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        DotsAndBoxesGameBoard(columns = 6, rows = 6, modifier) // Example with 5x5 dots grid
    }
}

@Composable
fun DotsAndBoxesScaffold(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        GameScreen(modifier)
    }
}
