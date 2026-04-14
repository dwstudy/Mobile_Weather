package com.example.weather.network

import com.example.weather.data.api.OpenMeteoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timezone: String = "Europe/Moscow",
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("hourly") hourly: String = "temperature_2m,weathercode,relative_humidity_2m,precipitation_probability,windspeed_10m",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weathercode,precipitation_probability_max",
        @Query("forecast_days") forecastDays: Int = 7
    ): OpenMeteoResponse
}
