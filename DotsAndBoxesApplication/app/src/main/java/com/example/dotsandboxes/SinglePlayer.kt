package com.example.dotsandboxes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.max

@Composable
fun SinglePlayerPage(modifier: Modifier, navController: NavController, model: GameStateViewModel) {
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
                DotsAndBoxesScaffold(modifier, model)
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
fun DotsAndBoxesScaffold(modifier: Modifier = Modifier, model: GameStateViewModel) {
    Column() {
        GameScreen(modifier = modifier, model = model)
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier, model: GameStateViewModel) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        DotsAndBoxesGameBoard(modifier = modifier, model = model)
    }
}

@Composable
fun DotsAndBoxesGameBoard(modifier: Modifier, model: GameStateViewModel) {
    val dotRadius = 8f
    val positionOfPoints: MutableList<MutableList<Pair<MutableFloatState, MutableFloatState>>> =
        remember {
            MutableList(model.columns) {
                MutableList(model.rows) {
                    Pair(mutableFloatStateOf(0f), mutableFloatStateOf(0f))
                }
            }
        }
    val density: Density = LocalDensity.current

    // State variables for button colors
    val horizontalButtonColors = remember {
        MutableList(model.columns - 1) {
            MutableList(model.rows) {
                mutableStateOf(Color.Transparent)
            }
        }
    }
    val verticalButtonColors = remember {
        MutableList(model.columns) {
            MutableList(model.rows - 1) {
                mutableStateOf(Color.Transparent)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            val minDimension = minOf(size.width, size.height)
            val spacing = minDimension / (max(model.columns, model.rows) + 1)
            val offsetX = (size.width - minDimension) / 2
            val offsetY = (size.height - minDimension) / 2

            // Draw dots
            for (i in 0 until model.columns) {
                for (j in 0 until model.rows) {
                    val x = offsetX + (i + 1) * spacing
                    val y = offsetY + (j + 1) * spacing
                    drawCircle(
                        color = Color.White,
                        radius = dotRadius,
                        center = Offset(x, y)
                    )
                    positionOfPoints[i][j].first.floatValue = x
                    positionOfPoints[i][j].second.floatValue = y
                }
            }
        }
        Box {
            // Draw horizontal buttons
            for (i in 0 until model.columns - 1) {
                for (j in 0 until model.rows) {
                    val x1 = positionOfPoints[i][j].first.floatValue
                    val y1 = positionOfPoints[i][j].second.floatValue
                    val x2 = positionOfPoints[i + 1][j].first.floatValue
                    val xMid = (x1 + x2) / 2
                    val yMid = y1
                    val buttonWidth = 170f
                    val buttonHeight = 20f

                    val xDp = with(density) { xMid.toDp() }
                    val yDp = with(density) { yMid.toDp() }
                    val buttonWidthDp = with(density) { buttonWidth.toDp() }
                    val buttonHeightDp = with(density) { buttonHeight.toDp() }

                    val buttonColor = horizontalButtonColors[i][j].value

                    Button(
                        onClick = {
                            model.buttonClicked(i,j)
                            horizontalButtonColors[i][j].value = model.currentPlayer.playerColor
                            model.nextPlayer()
                        },
                        modifier = Modifier
                            .offset(x = xDp - buttonWidthDp / 2, y = yDp - buttonHeightDp / 2)
                            .size(width = buttonWidthDp, height = buttonHeightDp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    ) {}
                }
            }

            // Draw vertical buttons
            for (i in 0 until model.columns) {
                for (j in 0 until model.rows - 1) {
                    val x1 = positionOfPoints[i][j].first.floatValue
                    val y1 = positionOfPoints[i][j].second.floatValue
                    val y2 = positionOfPoints[i][j + 1].second.floatValue
                    val xMid = x1
                    val yMid = (y1 + y2) / 2
                    val buttonWidth = 20f
                    val buttonHeight = 170f

                    val xDp = with(density) { xMid.toDp() }
                    val yDp = with(density) { yMid.toDp() }
                    val buttonWidthDp = with(density) { buttonWidth.toDp() }
                    val buttonHeightDp = with(density) { buttonHeight.toDp() }

                    val buttonColor = verticalButtonColors[i][j].value

                    Button(
                        onClick = {
                            model.buttonClicked(i,j)
                            verticalButtonColors[i][j].value = model.currentPlayer.playerColor
                            model.nextPlayer()
                        },
                        modifier = Modifier
                            .offset(x = xDp - buttonWidthDp / 2, y = yDp - buttonHeightDp / 2)
                            .size(width = buttonWidthDp, height = buttonHeightDp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    ) {}
                }
            }
        }
    }
}

















