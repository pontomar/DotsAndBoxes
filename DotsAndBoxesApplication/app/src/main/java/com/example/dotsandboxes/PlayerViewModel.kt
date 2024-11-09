package com.example.dotsandboxes

import android.app.Application
import android.graphics.Color
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    var listOfPlayers: MutableList<Player> = mutableListOf()

    fun createPlayerForSinglePlayer() {
        val player1 = Player(
            name = "Player1",
            playerColor = 1,
            numberOfFieldsWon = 0
        )
        val player2 = Player(
            name = "Player2",
            playerColor = 2,
            numberOfFieldsWon = 0
        )

        listOfPlayers.addAll(listOf(player1, player2))
    }

    fun createPlayerForMultiPlayer() {
        val player1 = Player(
            name = "Player1",
            playerColor = 1,
            numberOfFieldsWon = 0
        )
        val player2 = Player(
            name = "Player2",
            playerColor = 2,
            numberOfFieldsWon = 0
        )

        listOfPlayers.addAll(listOf(player1, player2))
    }


}
