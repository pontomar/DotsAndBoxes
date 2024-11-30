package com.example.dotsandboxes.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


class Player(
    var name: MutableState<String> = mutableStateOf(""),
    var playerColor: MutableState<Color> = mutableStateOf(Color.Transparent),
    var numberOfFieldsWon: Int = 0,
    var typeOfPlayer: MutableState<TypeOfPlayer> = mutableStateOf(TypeOfPlayer.HUMAN),
)