package com.example.dotsandboxes.model

enum class TypeOfPlayer{
    HUMAN, AI
}

enum class PlayerKey(val key: String) {
    PLAYER1NAMEKEY("player1Name"),
    PLAYER1COLORKEY("player1Color"),
    PLAYER2NAMEKEY("player2Name"),
    PLAYER2COLORKEY("player2Color"),
}