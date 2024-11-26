package com.example.dotsandboxes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dotsandboxes.controller.GameStateManager
import com.example.dotsandboxes.ui.theme.EarthYellow
import com.example.dotsandboxes.ui.theme.MutedRose
import com.example.dotsandboxes.ui.theme.PastelCyan
import com.example.dotsandboxes.viewModel.GameStateViewModel
import kotlinx.coroutines.delay
import org.w3c.dom.Text

@Composable
fun TutorialAddEdges(
    modifier: Modifier = Modifier,
    navController: NavController,
    model: GameStateViewModel
) {

    LaunchedEffect("bla") {
        model.gameStateManager.resetGame(model, 3, 3)
        model.playerManager.createPlayerForMultiPlayer()
        model.playerManager.listOfPlayers[0].playerColor.value = EarthYellow
        model.playerManager.listOfPlayers[1].playerColor.value = MutedRose

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
    //  resetAllowed: MutableState<Boolean>
) {

    LaunchedEffect("boxes") {
      //  val playerManager = model.playerManager
     //   model.gameStateManager = GameStateManager(3,3, playerManager = playerManager)
        model.gameStateManager.resetGame(model, 3,3)
        model.playerManager.createPlayerForMultiPlayer()
        model.buttonClicked(1, 1, true)
        model.playerManager.currentPlayer = model.playerManager.listOfPlayers[1]
        model.buttonClicked(1, 0, true)
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
    val hasCompletedBox = model.playerManager.currentPlayer.numberOfFieldsWon.value >= 1

    LaunchedEffect("try") {
       // val playerManager = model.playerManager
       // model.gameStateManager = GameStateManager(3,3, playerManager = playerManager)
        model.gameStateManager.resetGame(model, 3,3)
        model.playerManager.createPlayerForMultiPlayer()
        model.buttonClicked(1, 1, true)
        model.playerManager.currentPlayer = model.playerManager.listOfPlayers[1]
        model.buttonClicked(1, 0, true)
        model.buttonClicked(1, 0, false)
    }
    if (model.playerManager.currentPlayer.numberOfFieldsWon.value >= 1) {
        Text(
            "You did it. ", color = Color.Black,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
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
            .background(PastelCyan)
            .padding(16.dp, 40.dp, 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = content.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = content.description, fontSize = 14.sp, color = Color.Black, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            content.composable()
        }
    }
}

@Composable
fun ShowStartText(){
    Text("Hallo du startest das Tutorial")
}

data class TutorialContent(
    val title: String, val description: String,
    val composable: @Composable () -> Unit
)