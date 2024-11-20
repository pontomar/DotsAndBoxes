package com.example.dotsandboxes.viewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.example.dotsandboxes.controller.GameStateManager
import com.example.dotsandboxes.controller.PlayerManager
import com.example.dotsandboxes.model.Player
import com.example.dotsandboxes.model.TypeOfPlayer

class GameStateViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    var playerManager: PlayerManager = PlayerManager(context)
    var gameStateManager: GameStateManager = GameStateManager(rows = 5, columns = 5, playerManager)

    var singlePlayerModus: Boolean = false

    private val pointsToWinGame = (gameStateManager.rows.intValue - 1) * (gameStateManager.columns.intValue - 1) / 2 + 1
    var hasPlayerWon: MutableState<Boolean> = mutableStateOf(false)
    var isDraw: MutableState<Boolean> = mutableStateOf(false)

    val positionOfPoints: MutableList<MutableList<Pair<MutableFloatState, MutableFloatState>>> =
        MutableList(gameStateManager.columns.intValue) {
            MutableList(gameStateManager.rows.intValue) {
                Pair(mutableFloatStateOf(0f), mutableFloatStateOf(0f))
            }
        }



    // State variables for button colors
    val horizontalButtonColors =
        MutableList(gameStateManager.columns.intValue - 1) {
            MutableList(gameStateManager.rows.intValue) {
                mutableStateOf(Color.Transparent)
            }
        }

    val verticalButtonColors =
        MutableList(gameStateManager.columns.intValue) {
            MutableList(gameStateManager.rows.intValue - 1) {
                mutableStateOf(Color.Transparent)
            }
        }

    fun buttonClicked(xAxis: Int, yAxis: Int, isHorizontal: Boolean): Boolean {
        if (isHorizontal) {
            if (!gameStateManager.horizontalLines[xAxis][yAxis]) {
                gameStateManager.horizontalLines[xAxis][yAxis] = true
                horizontalButtonColors[xAxis][yAxis].value = playerManager.currentPlayer.playerColor.value

                val boxesCompleted = gameStateManager.checkForCompletedBoxes(xAxis, yAxis, isHorizontal)
                playerManager.currentPlayer.numberOfFieldsWon.value += boxesCompleted

                if (boxesCompleted == 0) {
                    nextPlayer()
                }
                isDraw()
                return hasPlayerWon()
            } else {
                // Line already drawn
                return false
            }
        } else {
            if (!gameStateManager.verticalLines[xAxis][yAxis]) {
                gameStateManager.verticalLines[xAxis][yAxis] = true
                verticalButtonColors[xAxis][yAxis].value = playerManager.currentPlayer.playerColor.value

                val boxesCompleted = gameStateManager.checkForCompletedBoxes(xAxis, yAxis, isHorizontal)
                playerManager.currentPlayer.numberOfFieldsWon.value += boxesCompleted

                if (boxesCompleted == 0) {
                    nextPlayer()
                }
                isDraw()
                return hasPlayerWon()
            } else {
                // Line already drawn
                return false
            }
        }
    }


    private fun hasPlayerWon(): Boolean {
        return playerManager.currentPlayer.numberOfFieldsWon.value >= pointsToWinGame
    }

    private fun isDraw(): Boolean {
        val numOfBoxes = (gameStateManager.columns.intValue - 1) * (gameStateManager.rows.intValue - 1)
        if (playerManager.listOfPlayers[0].numberOfFieldsWon.value == numOfBoxes / 2 && playerManager.listOfPlayers[1].numberOfFieldsWon.value == numOfBoxes / 2) {
            isDraw.value = true
        }

        return isDraw.value
    }


    private fun nextPlayer() {
        playerManager.currentPlayer = if (playerManager.currentPlayer == playerManager.listOfPlayers[0]) {
            playerManager.listOfPlayers[1]
        } else {
            playerManager.listOfPlayers[0]
        }

        if (singlePlayerModus && playerManager.currentPlayer == playerManager.listOfPlayers[1]) {
            clickButtonForAi()
        }
    }


    fun resetGame() {

//        gameStateManager.rows.intValue = 5
//        columns = 5

        // Reset the number of fields won for each player
        for (player in playerManager.listOfPlayers) {
            player.numberOfFieldsWon.value = 0
        }

        // Reset current player to the first player
        if (playerManager.listOfPlayers.isNotEmpty()) {
            playerManager.currentPlayer = playerManager.listOfPlayers[0]
        } else {
            playerManager.currentPlayer = Player()
        }

        resetElement(gameStateManager.horizontalLines, false)
        resetElement(gameStateManager.verticalLines, false)
        resetElement(gameStateManager.boxesOwned, -1)


        // Reset horizontal button colors
        for (i in horizontalButtonColors.indices) {
            for (j in horizontalButtonColors[i].indices) {
                horizontalButtonColors[i][j].value = Color.Transparent
            }
        }

        // Reset vertical button colors
        for (i in verticalButtonColors.indices) {
            for (j in verticalButtonColors[i].indices) {
                verticalButtonColors[i][j].value = Color.Transparent
            }
        }
    }

    private fun clickButtonForAi() {
        val gameWon: MutableState<Boolean> = mutableStateOf(false)
        val availableMoves = mutableListOf<Triple<Int, Int, Boolean>>()

        do {
            // Collect available horizontal lines
            for (x in 0 until gameStateManager.columns.intValue - 1) {
                for (y in 0 until gameStateManager.rows.intValue) {
                    if (!gameStateManager.horizontalLines[x][y]) {
                        availableMoves.add(Triple(x, y, true))
                    }
                }
            }
            // Collect available vertical lines
            for (x in 0 until gameStateManager.columns.intValue) {
                for (y in 0 until gameStateManager.rows.intValue - 1) {
                    if (!gameStateManager.verticalLines[x][y]) {
                        availableMoves.add(Triple(x, y, false))
                    }
                }
            }

            if (availableMoves.isNotEmpty()) {
                val randomMove = availableMoves.random()
                val xAxis = randomMove.first
                val yAxis = randomMove.second
                val isHorizontal = randomMove.third

                gameWon.value = buttonClicked(xAxis, yAxis, isHorizontal)

                if (gameWon.value) {
                    hasPlayerWon.value = true
                }
            }
        } while (playerManager.currentPlayer.typeOfPlayer.value == TypeOfPlayer.AI)
    }

    private fun <T> resetElement(element: MutableList<MutableList<T>>, item: T) {
        for (i in element.indices) {
            for (j in element[i].indices) {
                element[i][j] = item
            }
        }
    }
}
