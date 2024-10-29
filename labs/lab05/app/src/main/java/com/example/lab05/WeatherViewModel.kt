package com.example.lab05

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon


class WeatherViewModel(application: Application) :
    AndroidViewModel(application) {

    private val ENDPOINT: String =
        "https://api.open-meteo.com/v1/forecast?" +
                "latitude=46.67" +
                "&longitude=8.77" +
                "&daily=temperature_2m_max,temperature_2m_min" +
                ",weathercode" +
                "&current_weather=true" +
                "&timezone=Europe%2FBerlin"

    var temperature: MutableState<Float> = mutableFloatStateOf(-99.0F)
    var code: MutableState<Int> = mutableIntStateOf(-99)
    var codeTitle: MutableState<String> = mutableStateOf("")
    var dailyForecast: Daily? = null

    init {
        loadWeatherData()
        codeTitle.value = weatherCodeTitle(code.value)
    }

    private fun loadWeatherData() {

        val context = getApplication<Application>().applicationContext

        val requestQueue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET, ENDPOINT,
            { response ->
                try {
                    val parsedData = Klaxon().parse<Weather>(response)

                    temperature.value = parsedData?.currentWeather?.temperature ?: -99.0F
                    code.value = parsedData?.currentWeather?.weathercode ?: -99
                    dailyForecast?.time?.addAll(parsedData?.daily?.time ?: mutableListOf<Time>())
                    dailyForecast?.temperatureMax?.addAll(parsedData?.daily?.temperatureMax ?: mutableListOf<TemperatureMax>())
                    dailyForecast?.temperatureMin?.addAll(parsedData?.daily?.temperatureMin ?: mutableListOf<TemperatureMin>())
                    dailyForecast?.weathercode?.addAll(parsedData?.daily?.weathercode ?: mutableListOf<Weathercode>())
                    Log.i("Volley", parsedData.toString())

                } catch (e: Exception) {
                    Log.e("Volley", "Error parsing data: ${e.message}")
                }
            },
            Response.ErrorListener {
                Log.e("Volley", "Error loading data: $it")
            }
        )
        requestQueue.add(request)
    }

    fun weatherCodeTitle(weatherCode: Int?): String {
        return when (weatherCode) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2, 3 -> "Partly Cloudy"
            in 40..49 -> "Fog or Ice Fog"
            in 50..59 -> "Drizzle"
            in 60..69 -> "Rain"
            in 70..79 -> "Snow Fall"
            in 80..84 -> "Rain Showers"
            85, 86 -> "Snow Showers"
            87, 88 -> "Shower(s) of Snow Pellets"
            89, 90 -> "Hail"
            in 91..99 -> "Thunderstorm"
            else -> "unknown $weatherCode"
        }
    }

}
