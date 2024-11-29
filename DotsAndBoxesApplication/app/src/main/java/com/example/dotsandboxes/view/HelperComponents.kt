package com.example.dotsandboxes.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotsandboxes.model.Player
import com.example.dotsandboxes.ui.theme.CyanDark
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun StartPageButton(
    imageVector: ImageVector,
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
            Icon(
                imageVector = imageVector,
                contentDescription = "Navigation Button",
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .padding(5.dp),
                tint = Color.White
            )
            Text(text, color = Color.White, fontSize = 15.sp)
        }
    }
}

@Composable
fun GameButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(getResponsiveButtonWidth()),
        colors = ButtonDefaults.buttonColors(
            containerColor = CyanDark
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text, fontSize = 18.sp, color = Color.White, maxLines = 1,
            style = TextStyle(letterSpacing = 1.1.sp)
        )
    }
}

@Composable
fun PopUpInfoForUser(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun ColorPicker(player: Player) {
    val controller = rememberColorPickerController()

    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            player.playerColor.value = colorEnvelope.color
        }
    )
}

@Composable
fun getResponsiveButtonWidth(): Dp {
    val config = LocalConfiguration.current
    val buttonWidth = when {
        config.screenWidthDp < 600 -> 250.dp
        config.screenWidthDp < 840 -> 300.dp
        else -> 350.dp
    }
    return buttonWidth
}