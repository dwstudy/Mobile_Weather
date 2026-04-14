package com.example.weather.data.api

import com.squareup.moshi.Json

data class OpenMeteoResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val hourly: HourlyData,
    val daily: DailyData,
    @Json(name = "current_weather") val currentWeather: CurrentWeather? = null
)

data class HourlyData(
    val time: List<String>,
    @Json(name = "temperature_2m") val temperature: List<Double>,
    val weathercode: List<Int>,
    @Json(name = "relative_humidity_2m") val humidity: List<Int>? = null,
    @Json(name = "precipitation_probability") val precipitationProbability: List<Int>? = null,
    @Json(name = "windspeed_10m") val windSpeed: List<Double>? = null
)

data class DailyData(
    val time: List<String>,
    @Json(name = "temperature_2m_max") val temperatureMax: List<Double>,
    @Json(name = "temperature_2m_min") val temperatureMin: List<Double>,
    val weathercode: List<Int>,
    @Json(name = "precipitation_probability_max") val precipitationProbabilityMax: List<Int>? = null
)

data class CurrentWeather(
    val temperature: Double,
    val weathercode: Int,
    val windspeed: Double? = null
)
