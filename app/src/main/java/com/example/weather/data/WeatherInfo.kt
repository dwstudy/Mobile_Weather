package com.example.weather.data

import android.content.Context
import com.example.weather.R

data class WeatherInfo(
    val temperature: Double,
    val weatherCode: Int,
    val description: String,
    val humidity: Int? = null,
    val windSpeed: Double? = null,
    val precipitationProbability: Int? = null
)

data class HourlyForecast(
    val time: String,
    val temperature: Double,
    val weatherCode: Int,
    val description: String
)

data class DailyForecast(
    val date: String,
    val tempMax: Double,
    val tempMin: Double,
    val weatherCode: Int,
    val description: String,
    val precipitationProbability: Int? = null
)

fun getWeatherDescription(context: Context, code: Int): String {
    val res = context.resources
    return when (code) {
        0 -> res.getString(R.string.weather_clear)
        1 -> res.getString(R.string.weather_mostly_clear)
        2 -> res.getString(R.string.weather_partly_cloudy)
        3 -> res.getString(R.string.weather_overcast)
        45 -> res.getString(R.string.weather_fog)
        48 -> res.getString(R.string.weather_rime)
        51, 53, 55 -> res.getString(R.string.weather_drizzle)
        56, 57 -> res.getString(R.string.weather_freezing_drizzle)
        61, 63, 65 -> res.getString(R.string.weather_rain)
        66, 67 -> res.getString(R.string.weather_freezing_rain)
        71, 73, 75 -> res.getString(R.string.weather_snow)
        77 -> res.getString(R.string.weather_snow_grains)
        80, 81, 82 -> res.getString(R.string.weather_shower)
        85, 86 -> res.getString(R.string.weather_snow_shower)
        95 -> res.getString(R.string.weather_thunderstorm)
        96, 99 -> res.getString(R.string.weather_thunderstorm_hail)
        else -> res.getString(R.string.weather_unknown)
    }
}