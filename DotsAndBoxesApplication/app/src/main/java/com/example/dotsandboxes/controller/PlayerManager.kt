package com.example.dotsandboxes.controller

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.dotsandboxes.model.Player
import com.example.dotsandboxes.model.TypeOfPlayer
import com.example.dotsandboxes.ui.theme.EarthYellow
import com.example.dotsandboxes.ui.theme.MutedRose

class PlayerManager {
    var listOfPlayers: MutableList<Player> = mutableListOf()
    var currentPlayer: Player = Player()


    fun createPlayerForSinglePlayer() {
        val player1 = Player(
            name = mutableStateOf("Player 1"),
            playerColor = mutableStateOf(EarthYellow),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.HUMAN)
        )
        val player2 = Player(
            name = mutableStateOf("God Of AI"),
            playerColor = mutableStateOf(MutedRose),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.AI)
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        currentPlayer = listOfPlayers[0]
    }

    fun createPlayerForMultiPlayer() {
        val player1 = Player(
            name = mutableStateOf("Player 1"),
            playerColor = mutableStateOf(EarthYellow),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.HUMAN),
        )
        val player2 = Player(
            name = mutableStateOf("Player 2"),
            playerColor = mutableStateOf(MutedRose),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.HUMAN),
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        currentPlayer = listOfPlayers[0]
    }

    var selectedPlayer: Player = Player()
    var showPlayerInfoPopUp: MutableState<Boolean> = mutableStateOf(false)
    fun showPlayerInfo(player: Player) {
        selectedPlayer = player
        showPlayerInfoPopUp.value = true
    }
}