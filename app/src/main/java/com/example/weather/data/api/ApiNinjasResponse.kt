package com.example.weather.data.api

import com.squareup.moshi.Json

data class ApiNinjasCity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    @Json(name = "is_capital") val isCapital: Boolean? = null
)
