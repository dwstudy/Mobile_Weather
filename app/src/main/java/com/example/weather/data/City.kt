package com.example.weather.data

data class City(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String = "Europe/Moscow"
)
