package com.example.lab05

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class WeatherViewModel(application: Application) :
    AndroidViewModel(application) {

    var actualCity: City = City(
        "Sedrun",
        46.67F,
        8.77F,
        R.drawable.mountain_alp_culmatsch
    )

    var temperature: MutableState<Float> = mutableFloatStateOf(-99.0F)
    var code: MutableState<Int> = mutableIntStateOf(-99)
    var codeTitle: MutableState<String> = mutableStateOf("")
    var cities: MutableList<City> = mutableListOf()
    var dailyForecast: Daily? = Daily(
        time = mutableListOf(),
        temperatureMax = mutableListOf(),
        temperatureMin = mutableListOf(),
        weathercode = mutableListOf()
    )
    var showDropdown = mutableStateOf<Boolean>(false)


    init {
        loadWeatherData()
        codeTitle.value = weatherCodeTitle(code.value)
        cities.add(City("Sydney",
            -33.862587F,
            151.15915F,
            R.drawable.sydney)
        )
        cities.add(City("Los Angeles",
            34.02004F,
            -118.74137F,
            R.drawable.los_angeles)
        )
        cities.add(City("Sedrun",
            46.67F,
            8.77F,
            R.drawable.mountain_alp_culmatsch)
        )
    }

    fun loadWeatherData() {
        val endpoint = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=${actualCity.latitude}" +
                "&longitude=${actualCity.longitude}" +
                "&daily=temperature_2m_max,temperature_2m_min" +
                ",weathercode" +
                "&current_weather=true" +
                "&timezone=Europe%2FBerlin"

        val context = getApplication<Application>().applicationContext

        val requestQueue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET, endpoint,
            { response ->
                try {
                    val parsedData = Klaxon().parse<Weather>(response)
                    temperature.value = parsedData?.currentWeather?.temperature ?: -99.0F
                    code.value = parsedData?.currentWeather?.weathercode ?: -99

                    dailyForecast?.time?.clear()
                    dailyForecast?.temperatureMax?.clear()
                    dailyForecast?.temperatureMin?.clear()
                    dailyForecast?.weathercode?.clear()

                    dailyForecast?.time?.addAll(
                        parsedData?.daily?.time ?: mutableListOf()
                    )
                    dailyForecast?.temperatureMax?.addAll(
                        parsedData?.daily?.temperatureMax ?: mutableListOf()
                    )
                    dailyForecast?.temperatureMin?.addAll(
                        parsedData?.daily?.temperatureMin ?: mutableListOf()
                    )
                    dailyForecast?.weathercode?.addAll(
                        parsedData?.daily?.weathercode ?: mutableListOf()
                    )

                    Log.i("Volley", parsedData.toString())
                    Log.i(
                        "Volley dailyForecast temperatureMax",
                        parsedData?.daily?.temperatureMax.toString()
                    )
                    Log.i(
                        "Volley dailyForecast temperatureMin",
                        parsedData?.daily?.temperatureMin.toString()
                    )
                    Log.i(
                        "Volley dailyForecast weathercode",
                        parsedData?.daily?.weathercode.toString()
                    )
                    Log.i("Volley dailyForecast time", parsedData?.daily?.time.toString())

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeekday(time: String?): String {
        if (time == null) {
            return "Loading ... "
        }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(time, formatter)
        return date.dayOfWeek.toString()
    }

}
