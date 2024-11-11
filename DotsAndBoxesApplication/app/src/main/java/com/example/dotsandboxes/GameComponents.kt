package com.example.dotsandboxes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.max

@Composable
fun DotsAndBoxesScaffold(
    modifier: Modifier = Modifier,
    model: GameStateViewModel,
    navController: NavController
) {
    Column() {
        GameScreen(modifier = modifier, model = model, navController = navController)
    }
}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    model: GameStateViewModel,
    navController: NavController
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        DotsAndBoxesGameBoard(modifier = modifier, model = model, navController = navController)
    }
}

@Composable
fun DotsAndBoxesGameBoard(
    modifier: Modifier,
    model: GameStateViewModel,
    navController: NavController
) {
    val dotRadius = 8f
    val density: Density = LocalDensity.current

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
                    model.positionOfPoints[i][j].first.floatValue = x
                    model.positionOfPoints[i][j].second.floatValue = y
                }
            }

            // Draw filled boxes for completed squares
            for (i in 0 until model.columns - 1) {
                for (j in 0 until model.rows - 1) {
                    val ownerIndex = model.boxesOwned[i][j]
                    if (ownerIndex != -1) {
                        val playerColor = model.listOfPlayers[ownerIndex].playerColor
                        val x1 = model.positionOfPoints[i][j].first.floatValue
                        val y1 = model.positionOfPoints[i][j].second.floatValue
                        val size = spacing

                        // Draw rectangle for the completed box
                        drawRect(
                            color = playerColor.value,
                            topLeft = Offset(x1, y1),
                            size = Size(size, size)
                        )
                    }
                }
            }

        }
        Box {
            // Draw horizontal buttons
            for (i in 0 until model.columns - 1) {
                for (j in 0 until model.rows) {
                    val x1 = model.positionOfPoints[i][j].first.floatValue
                    val y1 = model.positionOfPoints[i][j].second.floatValue
                    val x2 = model.positionOfPoints[i + 1][j].first.floatValue
                    val xMid = (x1 + x2) / 2
                    val yMid = y1
                    val buttonWidth = 170f
                    val buttonHeight = 20f

                    val xDp = with(density) { xMid.toDp() }
                    val yDp = with(density) { yMid.toDp() }
                    val buttonWidthDp = with(density) { buttonWidth.toDp() }
                    val buttonHeightDp = with(density) { buttonHeight.toDp() }

                    val buttonColor = model.horizontalButtonColors[i][j].value

                    Button(
                        onClick = {
                            model.hasPlayerWon.value = model.buttonClicked(i, j, true)
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
                    val x1 = model.positionOfPoints[i][j].first.floatValue
                    val y1 = model.positionOfPoints[i][j].second.floatValue
                    val y2 = model.positionOfPoints[i][j + 1].second.floatValue
                    val xMid = x1
                    val yMid = (y1 + y2) / 2
                    val buttonWidth = 20f
                    val buttonHeight = 170f

                    val xDp = with(density) { xMid.toDp() }
                    val yDp = with(density) { yMid.toDp() }
                    val buttonWidthDp = with(density) { buttonWidth.toDp() }
                    val buttonHeightDp = with(density) { buttonHeight.toDp() }

                    val buttonColor = model.verticalButtonColors[i][j].value

                    Button(
                        onClick = {
                            model.hasPlayerWon.value = model.buttonClicked(i, j, false)
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
        if (model.hasPlayerWon.value) {
            PopUpInfoForUser(
                onDismissRequest = {
                    model.hasPlayerWon.value = false
                    navController.navigate("StartPage")
                    model.resetGame()
                },
                onConfirmation = {
                    model.hasPlayerWon.value = false
                    model.resetGame()
                },
                dialogTitle = "New Game?",
                dialogText = model.currentPlayer.name + " has won the game",
                icon = Icons.Default.Info
            )
        }
        if (model.isDraw.value) {
            PopUpInfoForUser(
                onDismissRequest = {
                    model.isDraw.value = false
                    navController.navigate("StartPage")
                    model.resetGame()
                },
                onConfirmation = {
                    model.isDraw.value = false
                    model.resetGame()
                },
                dialogTitle = "New Game?",
                dialogText = "None of You won the Game. Its a draw!",
                icon = Icons.Default.Info
            )
        }
    }
}

@Composable
fun PopUpInfoForUser(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}