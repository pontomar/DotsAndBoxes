package com.example.dotsandboxes.controller

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.dotsandboxes.model.Player
import com.example.dotsandboxes.model.PlayerKey
import com.example.dotsandboxes.model.TypeOfPlayer

class PlayerManager(context: Context) {
    var listOfPlayers: MutableList<Player> = mutableListOf()
    var currentPlayer: Player = Player()

    private val playersInformationSharedPreferences =
        context.getSharedPreferences("prefsfile", Context.MODE_PRIVATE)


    fun createPlayerForSinglePlayer() {
        val player1 = Player(
            name = mutableStateOf("Player 1"),
            playerColor = mutableStateOf(Color.Green),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.HUMAN),
            nameKey = mutableStateOf(PlayerKey.PLAYER1NAMEKEY.key),
            colorKey = mutableStateOf(PlayerKey.PLAYER1COLORKEY.key)
        )
        val player2 = Player(
            name = mutableStateOf("God Of AI"),
            playerColor = mutableStateOf(Color.Red),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.AI),
            nameKey = mutableStateOf(PlayerKey.PLAYER2NAMEKEY.key),
            colorKey = mutableStateOf(PlayerKey.PLAYER2COLORKEY.key)
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        savePlayersToPreferences(listOfPlayers[0])
        savePlayersToPreferences(listOfPlayers[1])

        currentPlayer = listOfPlayers[0]
    }

    fun createPlayerForMultiPlayer() {
        val player1 = Player(
            name = mutableStateOf("Player 1"),
            playerColor = mutableStateOf(Color.Green),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.HUMAN),
            nameKey = mutableStateOf(PlayerKey.PLAYER1NAMEKEY.key),
            colorKey = mutableStateOf(PlayerKey.PLAYER1COLORKEY.key)
        )
        val player2 = Player(
            name = mutableStateOf("Player 2"),
            playerColor = mutableStateOf(Color.Red),
            numberOfFieldsWon = mutableIntStateOf(0),
            typeOfPlayer = mutableStateOf(TypeOfPlayer.HUMAN),
            nameKey = mutableStateOf(PlayerKey.PLAYER2NAMEKEY.key),
            colorKey = mutableStateOf(PlayerKey.PLAYER2COLORKEY.key)
        )

        listOfPlayers.add(player1)
        listOfPlayers.add(player2)

        savePlayersToPreferences(listOfPlayers[0])
        savePlayersToPreferences(listOfPlayers[1])

        currentPlayer = listOfPlayers[0]
    }

    fun savePlayersToPreferences(player: Player) {
        val editor = playersInformationSharedPreferences.edit()

        editor.putString(player.nameKey.value, player.name.value)
        editor.putString(player.colorKey.value, player.playerColor.value.toString())
        editor.apply()
    }

    var selectedPlayer: Player = Player()
    var showPlayerInfoPopUp: MutableState<Boolean> = mutableStateOf(false)
    fun showPlayerInfo(player: Player) {
        selectedPlayer = player
        showPlayerInfoPopUp.value = true
    }
}