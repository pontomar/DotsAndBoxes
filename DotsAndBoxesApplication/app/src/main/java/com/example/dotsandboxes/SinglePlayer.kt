package com.example.dotsandboxes

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.max

@Composable
fun SinglePlayerPage(modifier: Modifier, navController: NavController) {
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
                StartPageButton(
                    emojiUnicode = "\uD83C\uDFE0",
                    text = "Home",
                    onClick = {
                        navController.navigate("StartPage")
                    },
                    Arrangement.Top
                )
                Text("Player 1", color = Color.White, fontSize = 20.sp)
                Spacer(modifier.heightIn(15.dp))
                Text("1", color = Color.White, fontSize = 20.sp)
            }
            // Game Column
            Column(
                modifier = modifier
                    .weight(2.5f)
                    .fillMaxHeight()
                    .padding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DotsAndBoxesScaffold(modifier)
            }
            // Player 2
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                StartPageButton(
                    emojiUnicode = "⚙\uFE0F",
                    text = "Info",
                    onClick = {
                        navController.navigate("InfoPage")
                    },
                    Arrangement.Top
                )
                Text("Player 2", color = Color.White, fontSize = 20.sp)
                Spacer(modifier.heightIn(15.dp))
                Text("2", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun DotsAndBoxesScaffold(modifier: Modifier = Modifier) {
    Column() {
        GameScreen(modifier = modifier)
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        DotsAndBoxesGameBoard(columns = 5, rows = 5, modifier = modifier)
    }
}

@Composable
fun DotsAndBoxesGameBoard(columns: Int, rows: Int, modifier: Modifier) {
    val dotRadius = 8f

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        // Calculate the minimum dimension to ensure the gameboard is quadratic
        val minDimension = minOf(size.width, size.height)
        val spacing = minDimension / (max(columns, rows) + 1)

        // Calculate the offset to center the quadratic board within the available space
        val offsetX = (size.width - minDimension) / 2
        val offsetY = (size.height - minDimension) / 2

        // Draw dots
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                val x = offsetX + (i + 1) * spacing
                val y = offsetY + (j + 1) * spacing
                drawCircle(
                    color = Color.White,
                    radius = dotRadius,
                    center = Offset(x, y)
                )
            }
        }
    }
}


















