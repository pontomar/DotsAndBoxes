package com.example.dotsandboxes

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel

class GameStateViewModel(application: Application) : AndroidViewModel(application) {
    var listOfPlayers: MutableList<Player> = mutableListOf()
    var currentPlayer: Player = Player()
    var rows: Int = 5
    var columns: Int = 5
    val pointsToWinGame = (rows - 1 * columns - 1) / 2 + 1
    var gameButtonGrid: MutableList<MutableList<Int>> =
        MutableList(columns) {
            MutableList(rows) {
                0
            }
        }


    fun createPlayerForSinglePlayer() {
        val player1 = Player(
            name = "Player1",
            playerColor = Color.Green,
            numberOfFieldsWon = 0
        )
        val player2 = Player(
            name = "Player2",
            playerColor = Color.Red,
            numberOfFieldsWon = 0
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        currentPlayer = listOfPlayers[0]

    }

    fun createPlayerForMultiPlayer() {
        val player1 = Player(
            name = "Player1",
            playerColor = Color.Green,
            numberOfFieldsWon = 0
        )
        val player2 = Player(
            name = "Player2",
            playerColor = Color.Red,
            numberOfFieldsWon = 0
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        currentPlayer = listOfPlayers[0]
    }

    fun nextPlayer() {
        if (currentPlayer == listOfPlayers[0]) {
            currentPlayer = listOfPlayers[1]
        } else {
            currentPlayer = listOfPlayers[0]
        }
    }

    fun buttonClicked(xAxis: Int, yAxis: Int): Boolean {
        gameButtonGrid[xAxis][yAxis] = listOfPlayers.indexOf(currentPlayer)
        checkForCompletedBoxes(xAxis, yAxis)
        val hasPlayerWon = hasPlayerWon()
        return hasPlayerWon
    }

    fun checkForCompletedBoxes(xAxis: Int, yAxis: Int) {
        val maxX = gameButtonGrid.size - 1
        val maxY = gameButtonGrid[0].size - 1

        val topEdge = if (xAxis - 1 >= 0) gameButtonGrid[xAxis - 1][yAxis] else gameButtonGrid[0][yAxis]
        val bottomEdge = if (xAxis + 1 <= maxX) gameButtonGrid[xAxis + 1][yAxis] else gameButtonGrid[maxX][yAxis]
        val leftEdge = if (yAxis - 1 >= 0) gameButtonGrid[xAxis][yAxis - 1] else gameButtonGrid[xAxis][0]
        val rightEdge = if (yAxis + 1 <= maxY) gameButtonGrid[xAxis][yAxis + 1] else gameButtonGrid[xAxis][maxY]

        if (topEdge > 0 && bottomEdge > 0 && leftEdge > 0 && rightEdge > 0) {
            if (gameButtonGrid[xAxis][yAxis] == 0) {
                Log.i("PointWon", "${currentPlayer.name}: ${currentPlayer.numberOfFieldsWon}")
                gameButtonGrid[xAxis][yAxis] = listOfPlayers.indexOf(currentPlayer)
                currentPlayer.numberOfFieldsWon += 1
                Log.i("PointWon", "${currentPlayer.name}: ${currentPlayer.numberOfFieldsWon}")
            }
        }
    }



    fun hasPlayerWon(): Boolean {
        if (currentPlayer.numberOfFieldsWon >= pointsToWinGame) {
            return true
        } else {
            return false
        }
    }

}
