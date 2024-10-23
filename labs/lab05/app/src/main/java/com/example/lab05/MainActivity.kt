package com.example.lab05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab05.ui.theme.Lab05Theme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab05Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        StartPage(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StartPage(modifier: Modifier = Modifier, model: WeatherViewModel = viewModel()) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.mountain_alp_culmatsch),
            contentDescription = "Mountain with a bit of snow",
            contentScale = ContentScale.Crop,
            modifier = modifier.matchParentSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.matchParentSize().padding(24.dp)
        ) {
            TextElements(model.weatherCodeTitle(model.code.value), 16, FontWeight.Bold)
            TextElements("Sedrun", 16, fontStyle = FontStyle.Italic)
            TextElements("${model.temperature.value}ºC", 96)
        }
    }
}

@Composable
fun TextElements(text: String, fontSize : Int, fontWeight: FontWeight = FontWeight.Normal, fontStyle: FontStyle = FontStyle.Normal){
    Text(text = text, fontSize = fontSize.sp, fontWeight = fontWeight, color = androidx.compose.ui.graphics.Color.White, fontStyle = fontStyle)
}
