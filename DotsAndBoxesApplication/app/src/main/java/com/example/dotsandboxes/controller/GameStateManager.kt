package com.example.dotsandboxes.controller

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf

class GameStateManager(rows: Int, columns: Int, playerManager: PlayerManager) {
    var rows: MutableIntState = mutableIntStateOf(rows)
    var columns: MutableIntState = mutableIntStateOf(columns)
    var playerManager: PlayerManager = playerManager

    val horizontalLines = MutableList(columns) {
        MutableList(rows + 1) {
            false
        }
    }

    val verticalLines = MutableList(columns + 1) {
        MutableList(rows) {
            false
        }
    }

    val boxesOwned = MutableList(columns - 1) {
        MutableList(rows - 1) {
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
                        boxesOwned[xAxis][yAxis - 1] = playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
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
                        boxesOwned[xAxis][yAxis] = playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
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
                        boxesOwned[xAxis - 1][yAxis] = playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
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
                        boxesOwned[xAxis][yAxis] = playerManager.listOfPlayers.indexOf(playerManager.currentPlayer)
                        boxesCompleted++
                    }
                }
            }
        }
        return boxesCompleted
    }

}