package com.example.dotsandboxes

import android.app.Application
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel

class GameStateViewModel(application: Application) : AndroidViewModel(application) {
    var listOfPlayers: MutableList<Player> = mutableListOf()
    var currentPlayer: Player = Player()
    var rows: Int = 5
    var columns: Int = 5
    private val pointsToWinGame = (rows - 1) * (columns - 1) / 2 + 1

    val positionOfPoints: MutableList<MutableList<Pair<MutableFloatState, MutableFloatState>>> =
        MutableList(columns) {
            MutableList(rows) {
                Pair(mutableFloatStateOf(0f), mutableFloatStateOf(0f))
            }
        }

    // Store the state of the horizontal and vertical lines
    private val horizontalLines = MutableList(columns) { MutableList(rows + 1) { false } }
    private val verticalLines = MutableList(columns + 1) { MutableList(rows) { false } }

    // Store the ownership of boxes
    val boxesOwned = MutableList(columns - 1) {
        MutableList(rows - 1) {
            -1
        }
    }

    // State variables for button colors
    val horizontalButtonColors =
        MutableList(columns - 1) {
            MutableList(rows) {
                mutableStateOf(Color.Transparent)
            }
        }

    val verticalButtonColors =
        MutableList(columns) {
            MutableList(rows - 1) {
                mutableStateOf(Color.Transparent)
            }
        }

    fun createPlayerForSinglePlayer() {
        val player1 = Player(
            name = "Player1",
            playerColor = Color.Green,
            numberOfFieldsWon = mutableIntStateOf(0)
        )
        val player2 = Player(
            name = "Player2",
            playerColor = Color.Red,
            numberOfFieldsWon = mutableIntStateOf(0)
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        currentPlayer = listOfPlayers[0]

    }

    fun createPlayerForMultiPlayer() {
        val player1 = Player(
            name = "Player1",
            playerColor = Color.Green,
            numberOfFieldsWon = mutableIntStateOf(0)
        )
        val player2 = Player(
            name = "Player2",
            playerColor = Color.Red,
            numberOfFieldsWon = mutableIntStateOf(0)
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        currentPlayer = listOfPlayers[0]
    }

    fun buttonClicked(xAxis: Int, yAxis: Int, isHorizontal: Boolean): Boolean {
        if (isHorizontal) {
            if (!horizontalLines[xAxis][yAxis]) {
                horizontalLines[xAxis][yAxis] = true
                horizontalButtonColors[xAxis][yAxis].value = currentPlayer.playerColor

                val boxesCompleted = checkForCompletedBoxes(xAxis, yAxis, isHorizontal)
                currentPlayer.numberOfFieldsWon.value += boxesCompleted

                if (boxesCompleted == 0) {
                    nextPlayer()
                }

                return hasPlayerWon()
            } else {
                // Line already drawn
                return false
            }
        } else {
            if (!verticalLines[xAxis][yAxis]) {
                verticalLines[xAxis][yAxis] = true
                verticalButtonColors[xAxis][yAxis].value = currentPlayer.playerColor

                val boxesCompleted = checkForCompletedBoxes(xAxis, yAxis, isHorizontal)
                currentPlayer.numberOfFieldsWon.value += boxesCompleted

                if (boxesCompleted == 0) {
                    nextPlayer()
                }

                return hasPlayerWon()
            } else {
                // Line already drawn
                return false
            }
        }
    }

    private fun checkForCompletedBoxes(xAxis: Int, yAxis: Int, isHorizontal: Boolean): Int {
        var boxesCompleted = 0

        if (isHorizontal) {
            // Check box above
            if (yAxis > 0 && xAxis < columns - 1) {
                if (horizontalLines[xAxis][yAxis - 1] &&
                    verticalLines[xAxis][yAxis - 1] &&
                    verticalLines[xAxis + 1][yAxis - 1] &&
                    horizontalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis][yAxis - 1] == -1) {
                        boxesOwned[xAxis][yAxis - 1] = listOfPlayers.indexOf(currentPlayer)
                        boxesCompleted++
                    }
                }
            }
            // Check box below
            if (yAxis < rows && xAxis < columns - 1) {
                if (horizontalLines[xAxis][yAxis + 1] &&
                    verticalLines[xAxis][yAxis] &&
                    verticalLines[xAxis + 1][yAxis] &&
                    horizontalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis][yAxis] == -1) {
                        boxesOwned[xAxis][yAxis] = listOfPlayers.indexOf(currentPlayer)
                        boxesCompleted++
                    }
                }
            }
        } else {
            // Check box to the left
            if (xAxis > 0 && yAxis < rows - 1) {
                if (verticalLines[xAxis - 1][yAxis] &&
                    horizontalLines[xAxis - 1][yAxis] &&
                    horizontalLines[xAxis - 1][yAxis + 1] &&
                    verticalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis - 1][yAxis] == -1) {
                        boxesOwned[xAxis - 1][yAxis] = listOfPlayers.indexOf(currentPlayer)
                        boxesCompleted++
                    }
                }
            }
            // Check box to the right
            if (xAxis < columns && yAxis < rows - 1) {
                if (verticalLines[xAxis + 1][yAxis] &&
                    horizontalLines[xAxis][yAxis] &&
                    horizontalLines[xAxis][yAxis + 1] &&
                    verticalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis][yAxis] == -1) {
                        boxesOwned[xAxis][yAxis] = listOfPlayers.indexOf(currentPlayer)
                        boxesCompleted++
                    }
                }
            }
        }

        return boxesCompleted
    }

    private fun hasPlayerWon(): Boolean {
        return currentPlayer.numberOfFieldsWon.value >= pointsToWinGame
    }


    private fun nextPlayer() {
        currentPlayer = if (currentPlayer == listOfPlayers[0]) {
            listOfPlayers[1]
        } else {
            listOfPlayers[0]
        }
    }

    fun resetGame() {
        // Reset the number of fields won for each player
        for (player in listOfPlayers) {
            player.numberOfFieldsWon.value = 0
        }

        // Reset current player to the first player
        if (listOfPlayers.isNotEmpty()) {
            currentPlayer = listOfPlayers[0]
        } else {
            currentPlayer = Player()
        }

        // Reset the state of horizontal lines
        for (i in horizontalLines.indices) {
            for (j in horizontalLines[i].indices) {
                horizontalLines[i][j] = false
            }
        }

        // Reset the state of vertical lines
        for (i in verticalLines.indices) {
            for (j in verticalLines[i].indices) {
                verticalLines[i][j] = false
            }
        }

        // Reset ownership of boxes
        for (i in boxesOwned.indices) {
            for (j in boxesOwned[i].indices) {
                boxesOwned[i][j] = -1
            }
        }

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

}
