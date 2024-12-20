package com.example.dotsandboxes.controller

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import com.example.dotsandboxes.model.Player
import com.example.dotsandboxes.viewModel.GameStateViewModel

class GameStateManager(inputRows: Int, inputColumns: Int, playerManager: PlayerManager) {
    var rows: MutableIntState = mutableIntStateOf(inputRows)
    var columns: MutableIntState = mutableIntStateOf(inputColumns)
    var playerManager: PlayerManager = playerManager

    val horizontalLines = MutableList(columns.intValue) {
        MutableList(rows.intValue + 1) {
            false
        }
    }

    val verticalLines = MutableList(columns.intValue + 1) {
        MutableList(rows.intValue) {
            false
        }
    }

    val boxesOwned = MutableList(columns.intValue - 1) {
        MutableList(rows.intValue - 1) {
            -1
        }
    }

    fun checkForCompletedBoxes(xAxis: Int, yAxis: Int, isHorizontal: Boolean): Int {
        var boxesCompleted = 0

        if (isHorizontal) {
            // Check box above
            if (yAxis > 0 && xAxis < columns.intValue - 1) {
                if (horizontalLines[xAxis][yAxis - 1] &&
                    verticalLines[xAxis][yAxis - 1] &&
                    verticalLines[xAxis + 1][yAxis - 1] &&
                    horizontalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis][yAxis - 1] == -1) {
                        boxesOwned[xAxis][yAxis - 1] =
                            playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
                        boxesCompleted++
                    }
                }
            }
            // Check box below
            if (yAxis < rows.intValue && xAxis < columns.intValue - 1) {
                if (horizontalLines[xAxis][yAxis + 1] &&
                    verticalLines[xAxis][yAxis] &&
                    verticalLines[xAxis + 1][yAxis] &&
                    horizontalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis][yAxis] == -1) {
                        boxesOwned[xAxis][yAxis] =
                            playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
                        boxesCompleted++
                    }
                }
            }
        } else {
            // Check box to the left
            if (xAxis > 0 && yAxis < rows.intValue - 1) {
                if (verticalLines[xAxis - 1][yAxis] &&
                    horizontalLines[xAxis - 1][yAxis] &&
                    horizontalLines[xAxis - 1][yAxis + 1] &&
                    verticalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis - 1][yAxis] == -1) {
                        boxesOwned[xAxis - 1][yAxis] =
                            playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
                        boxesCompleted++
                    }
                }
            }
            // Check box to the right
            if (xAxis < columns.intValue && yAxis < rows.intValue - 1) {
                if (verticalLines[xAxis + 1][yAxis] &&
                    horizontalLines[xAxis][yAxis] &&
                    horizontalLines[xAxis][yAxis + 1] &&
                    verticalLines[xAxis][yAxis]
                ) {
                    if (boxesOwned[xAxis][yAxis] == -1) {
                        boxesOwned[xAxis][yAxis] =
                            playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
                        boxesCompleted++
                    }
                }
            }
        }
        return boxesCompleted
    }


    fun resetGame(model: GameStateViewModel, resetRowsTo: Int = 5, resetColumnsTo: Int = 5) {
        rows.intValue = resetRowsTo
        columns.intValue = resetColumnsTo
        // Reset the number of fields won for each player
        model.playerManager.listOfPlayers.clear()
        if (model.singlePlayerModus) {
            model.playerManager.createPlayerForSinglePlayer()
        } else {
            model.playerManager.createPlayerForMultiPlayer()
            playerManager.listOfPlayers[1].numberOfFieldsWon.intValue = 0
        }
        playerManager.listOfPlayers[0].numberOfFieldsWon.intValue = 0


        // Reset current player to the first player
        if (playerManager.listOfPlayers.isNotEmpty()) {
            playerManager.currentPlayer = playerManager.listOfPlayers[0]
        } else {
            playerManager.currentPlayer = Player()
        }

        resetElement(horizontalLines, false)
        resetElement(verticalLines, false)
        resetElement(boxesOwned, -1)


        // Reset horizontal button colors
        for (i in model.horizontalButtonColors.indices) {
            for (j in model.horizontalButtonColors[i].indices) {
                model.horizontalButtonColors[i][j].value = Player()
            }
        }


        // Reset vertical button colors
        for (i in model.verticalButtonColors.indices) {
            for (j in model.verticalButtonColors[i].indices) {
                model.verticalButtonColors[i][j].value = Player()
            }
        }
    }

    private fun <T> resetElement(element: MutableList<MutableList<T>>, item: T) {
        for (i in element.indices) {
            for (j in element[i].indices) {
                element[i][j] = item
            }
        }
    }

}