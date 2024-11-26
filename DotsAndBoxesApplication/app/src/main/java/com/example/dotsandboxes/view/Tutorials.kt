package com.example.dotsandboxes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dotsandboxes.ui.theme.CyanDark
import com.example.dotsandboxes.viewModel.GameStateViewModel


@Composable
fun TutorialWelcomeCard(
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            text = "Swipe\nto start the tutorial."
        )

        Spacer(
            modifier = Modifier.height(20.dp),
        )

        Text(
            color = Color.White,
            fontSize = 14.sp,
            text = "Duration: 2 minutes"
        )
    }
}


@Composable
fun TutorialAddEdges(
    modifier: Modifier,
    navController: NavController,
    model: GameStateViewModel,
) {
    LaunchedEffect(Unit) {
        model.gameStateManager.resetGame(model, 3, 3)
        model.playerManager.createPlayerForMultiPlayer()
        model.buttonClicked(1, 2, true)
        model.playerManager.currentPlayer = model.playerManager.listOfPlayers[1]
        model.buttonClicked(1, 1, false)
        model.buttonClicked(1, 0, true)
        model.buttonClicked(0, 1, true)
    }

    DotsAndBoxesScaffold(
        modifier, model, navController
    )
}

@Composable
fun TutorialCaptureBoxes(
    modifier: Modifier,
    navController: NavController,
    model: GameStateViewModel,
) {
    LaunchedEffect(Unit) {
        model.gameStateManager.resetGame(model, 3, 3)
        model.playerManager.createPlayerForMultiPlayer()
        model.buttonClicked(1, 1, true)
        model.playerManager.currentPlayer = model.playerManager.listOfPlayers[1]
        model.buttonClicked(1, 0, true)
        model.buttonClicked(0, 0, true)
        model.buttonClicked(0, 0, false)
        model.buttonClicked(0, 1, true)
        model.buttonClicked(0, 1, false)
        model.buttonClicked(1, 1, false)
        model.buttonClicked(1, 0, false)
    }

    DotsAndBoxesScaffold(
        modifier, model, navController
    )
}


@Composable
fun TutorialCaptureTheBox(
    modifier: Modifier,
    navController: NavController,
    model: GameStateViewModel,
) {

    LaunchedEffect(Unit) {
        model.gameStateManager.resetGame(model, 3, 3)
        model.playerManager.createPlayerForMultiPlayer()
        model.buttonClicked(1, 1, true)
        model.buttonClicked(1, 0, true)
        model.playerManager.currentPlayer = model.playerManager.listOfPlayers[1]
        model.buttonClicked(1, 0, false)
    }
    if (model.playerManager.listOfPlayers
            [0].numberOfFieldsWon.value >= 1 || model.playerManager.listOfPlayers[1].numberOfFieldsWon.value >= 1
    ) {
        Text(
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            text = "You did it! Let's play."
        )

    }
    DotsAndBoxesScaffold(
        modifier, model, navController
    )
}

@Composable
fun TutorialCard(content: TutorialContent) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyanDark)
            .padding(16.dp, 40.dp, 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = content.title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = content.description,
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)

                ) { content.composable() }
            }
        }
    }
}

data class TutorialContent(
    val title: String, val description: String,
    val composable: @Composable () -> Unit
)