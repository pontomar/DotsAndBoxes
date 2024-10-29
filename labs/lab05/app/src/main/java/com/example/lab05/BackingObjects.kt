package com.example.lab05

import android.annotation.SuppressLint
import androidx.compose.ui.text.TextStyle
import com.beust.klaxon.Json
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Weather(
    @Json(name = "current_weather") val currentWeather: CurrentWeather,
    @Json(name = "daily") val daily: Daily
)

data class CurrentWeather(
    val temperature: Float,
    val weathercode: Int
)

data class Daily(
    val time: MutableList<Time>,
    @Json(name = "temperature_2m_max") val temperatureMax: MutableList<TemperatureMax>,
    @Json(name = "temperature_2m_min") val temperatureMin: MutableList<TemperatureMin>,
    val weathercode: MutableList<Weathercode>
)

data class Time(val time: String) {
    val weekDay: DayOfWeek
        @SuppressLint("NewApi")
        get() {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(time, formatter)
            val weekDay = date.dayOfWeek
            return weekDay
        }
}

data class TemperatureMax(val Temp: Float)

data class TemperatureMin(val Temp: Float)

data class Weathercode(val Code: Int)
