package com.example.lab05

import com.beust.klaxon.Json

data class Weather(
    @Json(name = "current_weather") val currentWeather: CurrentWeather,
)



data class CurrentWeather(
    val temperature: Float,
    val weathercode: Int
)
