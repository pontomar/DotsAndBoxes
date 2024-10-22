package com.example.lab05

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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
                "&current_weather=true" +
                "&timezone=Europe%2FBerlin"

    var entries = mutableStateListOf<WeatherboardEntry>()

    init {
        loadWeatherData()
    }

    private fun loadWeatherData() {

        val context = getApplication<Application>().applicationContext

        val requestQueue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET, ENDPOINT,
            { response ->
                Log.i("Volley", response)
                val s = Klaxon().parse<Weatherboard>(response)
                entries.addAll(s?.weatherboad ?: mutableListOf<WeatherboardEntry>())
            },
            Response.ErrorListener {
                Log.e("Volley", "Error loading data: $it")
            }
        )

        requestQueue.add(request)
    }
}
