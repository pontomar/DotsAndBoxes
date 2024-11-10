package com.example.dotsandboxes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import kotlin.math.max
import com.example.dotsandboxes.MinimalDialog as MinimalDialog

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
                StartPageButton(
                    emojiUnicode = "\uD83C\uDFE0",
                    text = "Home",
                    onClick = {
                        navController.navigate("StartPage")
                    },
                    Arrangement.Top
                )
                Text("Player 1", color = model.listOfPlayers[0].playerColor, fontSize = 20.sp)
                Spacer(modifier.heightIn(15.dp))
                Text(model.listOfPlayers[0].numberOfFieldsWon.toString(), color = model.listOfPlayers[0].playerColor, fontSize = 20.sp)
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
                Text("Player 2", color = model.listOfPlayers[1].playerColor, fontSize = 20.sp)
                Spacer(modifier.heightIn(15.dp))
                Text(model.listOfPlayers[1].numberOfFieldsWon.toString(), color = model.listOfPlayers[1].playerColor, fontSize = 20.sp)

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
    val density: Density = LocalDensity.current
    val showWinnerMessage = remember { mutableStateOf(false) }

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
                            color = playerColor,
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
                            showWinnerMessage.value = model.buttonClicked(i,j, true)
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
                            showWinnerMessage.value = model.buttonClicked(i,j, false)
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
        if (showWinnerMessage.value) {
            MinimalDialog()
        }
    }
}


@Composable
fun MinimalDialog() {
    Dialog(onDismissRequest = {  }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "This is a minimal dialog",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }
}















