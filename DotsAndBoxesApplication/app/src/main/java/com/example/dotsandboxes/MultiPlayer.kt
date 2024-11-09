package com.example.dotsandboxes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun MultiPlayerPage(modifier: Modifier = Modifier, navController: NavController){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text("This is the MultiPlayerPage", color = Color.White)
    }
}