package com.example.dotsandboxes

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    var listOfPlayers: MutableList<Player> = mutableListOf()
    var currentPlayer: Player = Player()

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

    fun nextPlayer(){
        if (currentPlayer == listOfPlayers[0]){
            currentPlayer = listOfPlayers[1]
        }else{
            currentPlayer = listOfPlayers[0]
        }
    }
}
