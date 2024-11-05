package com.example.lab05

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab05.ui.theme.Lab05Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartPage(modifier: Modifier = Modifier, model: WeatherViewModel = viewModel()) {
    val daily = model.dailyForecast

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painterResource(id = model.actualCity.picture),
            contentDescription = "Mountain with a bit of snow",
            contentScale = ContentScale.Crop,
            modifier = modifier.matchParentSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .matchParentSize()
                .padding(24.dp)
        ) {
            if (daily != null &&
                daily.time.isNotEmpty() &&
                daily.weathercode.isNotEmpty() &&
                daily.temperatureMin.isNotEmpty() &&
                daily.temperatureMax.isNotEmpty() &&
                model.code.value != -99 &&
                model.temperature.value != -99.0F
            ) {
                Box() {
                    Row() {
                        Text(
                            model.actualCity.cityName,
                            fontSize = 24.sp,
                            modifier = Modifier.clickable {
                                model.showDropdown.value = true
                            }
                        )

                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_arrow_downward_24
                            ),
                            contentDescription = "Arrow down"
                        )
                    }

                    DropdownMenu(
                        expanded = model.showDropdown.value,
                        onDismissRequest = {
                            model.showDropdown.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        model.cities.forEach {
                            DropdownMenuItem(
                                {
                                    Text(it.cityName, fontSize = 24.sp)
                                }, onClick = {
                                    model.actualCity = it
                                    model.showDropdown.value = false
                                    model.loadWeatherData()
                                })
                        }
                    }
                }

                TextElements(model.weatherCodeTitle(model.code.value), 16, FontWeight.Bold)
//                TextElements(model.actualCity.cityName, 16, fontStyle = FontStyle.Italic)
                TextElements("${model.temperature.value}ºC", 96)

                val itemCount = minOf(5, daily.time.size)
                for (i in 0 until itemCount) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Weekday
                        val weekDay = model.getWeekday(daily.time[i])
                        DailyText(
                            text = weekDay,
                            fontSize = 12,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold
                        )

                        // Weather Code Title
                        val weatherCode = daily.weathercode[i]
                        DailyText(
                            text = model.weatherCodeTitle(weatherCode),
                            fontSize = 12,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold
                        )

                        // Minimum Temperature
                        val tempMin = daily.temperatureMin[i]
                        DailyText(
                            text = "${tempMin}°C",
                            fontSize = 12,
                            textAlign = TextAlign.Right,
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            if (tempMin > model.temperature.value) {
                                painterResource(id = R.drawable.baseline_arrow_upward_24)
                            } else {
                                painterResource(id = R.drawable.baseline_arrow_downward_24)
                            },
                            contentDescription = "Arrow"
                        )

                        // Maximum Temperature
                        val tempMax = daily.temperatureMax[i]
                        DailyText(
                            text = "${tempMax}°C",
//                            text = "${tempMax}°C ↓",
                            fontSize = 12,
                            textAlign = TextAlign.Right,
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            if (tempMax > model.temperature.value) {
                                painterResource(id = R.drawable.baseline_arrow_upward_24)
                            } else {
                                painterResource(id = R.drawable.baseline_arrow_downward_24)
                            },
                            contentDescription = "Arrow",
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
            } else {
                // Show a loading indicator or placeholder
                Text(
                    text = "Loading data for Sedrun...",
                    fontSize = 24.sp,
                    color = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun TextElements(
    text: String,
    fontSize: Int,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        color = androidx.compose.ui.graphics.Color.White,
        fontStyle = fontStyle
    )
}

@Composable
fun DailyText(
    text: String,
    fontSize: Int,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    textAlign: TextAlign = TextAlign.Left,
    modifier: Modifier
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        color = androidx.compose.ui.graphics.Color.White,
        fontStyle = fontStyle,
        textAlign = textAlign,
        modifier = modifier

    )
}
