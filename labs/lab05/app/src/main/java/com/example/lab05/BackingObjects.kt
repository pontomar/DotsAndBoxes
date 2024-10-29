package com.example.lab05

import android.annotation.SuppressLint
import com.beust.klaxon.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Weather(
    @Json(name = "current_weather") val currentWeather: CurrentWeather,
    @Json(name = "daily") val daily: Daily
)

data class CurrentWeather(
    val temperature: Float,
    val weathercode: Int
)

data class Daily(
    val time: MutableList<String>,
    @Json(name = "temperature_2m_max") val temperatureMax: MutableList<Float>,
    @Json(name = "temperature_2m_min") val temperatureMin: MutableList<Float>,
    val weathercode: MutableList<Int>
)

data class Time(val time: String)

data class TemperatureMax(val temp: Float)

data class TemperatureMin(val temp: Float)

data class Weathercode(val code: Int)
