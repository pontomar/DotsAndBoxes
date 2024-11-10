package com.example.dotsandboxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun StartPageButton(
    emojiUnicode: String,
    text: String,
    onClick: () -> Unit,
    verticalArrangement: Arrangement.Vertical
) {
    TextButton(
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = verticalArrangement
        ) {
            Text(emojiUnicode, fontSize = 40.sp)
            Text(text, fontSize = 20.sp)
        }
    }
}