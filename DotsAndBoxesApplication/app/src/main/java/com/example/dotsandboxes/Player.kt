package com.example.dotsandboxes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color

class Player(
    var name: String = "",
    var playerColor: Color = Color.Transparent,
    var numberOfFieldsWon: MutableState<Int> = mutableIntStateOf(0)
)