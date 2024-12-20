package com.example.dotsandboxes.view

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aghajari.compose.lazyswipecards.LazySwipeCards
import com.example.dotsandboxes.viewModel.GameStateViewModel


@Composable
fun InfoPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    model: GameStateViewModel,
) {
    val activity = LocalContext.current as? Activity

    val tutorialContentList = remember {
        mutableStateListOf(
            TutorialContent(
                "How to Play",
                "Dots and Boxes",
                { TutorialWelcomeCard() }
            ),
            TutorialContent(
                "Connect the Dots",
                "Take turns with your opponent" + " to add edges.",
                { TutorialAddEdges(modifier, navController, model) }
            ),
            TutorialContent(
                "Capture Boxes",
                "The goal is to capture as many boxes as possible.",
                { TutorialCaptureBoxes(modifier, navController, model) }
            ),
            TutorialContent(
                "Ready to Explore the Game in Single Player Mode?",
                "",
                { TutorialCaptureTheBox(navController, model) }
            )
        )
    }

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
        LazySwipeCards(
            modifier
                .matchParentSize()
                .offset(y = (-40).dp),
            cardShape = RoundedCornerShape(16.dp),
            cardContentColor = Color.White,
            cardShadowElevation = 4.dp,
            visibleItemCount = 3,
            rotateDegree = 15.0f,
            translateSize = 24.dp,
            swipeThreshold = 0.5f,
            scaleFactor = ScaleFactor(scaleX = 0.1f, scaleY = 0.1f),
            contentPadding = PaddingValues(vertical = 24.dp * 4, horizontal = 24.dp),

            )
        {
            onSwiped() { item, _ ->
                model.gameStateManager.resetGame(model, 3, 3)
                tutorialContentList.add(item as TutorialContent)

            }

            items(tutorialContentList) {
                TutorialCard(it)
            }
        }
    }

    Row {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            StartPageButton(
                imageVector = Icons.Outlined.Home,
                text = "Home",
                onClick = {

                    navController.navigate("StartPage")
                    model.gameStateManager.resetGame(model, 5, 5)
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
                imageVector = Icons.Outlined.Info,
                text = "Info",
                onClick = {
                    navController.navigate("InfoPage")
                    model.gameStateManager.resetGame(model)
                },
                Arrangement.Bottom
            )
        }
    }
}


