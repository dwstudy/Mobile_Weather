package com.example.weather.network

import com.example.weather.data.api.ApiNinjasCity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiNinjasApi {

    @GET("v1/city")
    suspend fun getCity(
        @Query("name") cityName: String,
        @Query("limit") limit: Int = 1,
        @Header("X-Api-Key") apiKey: String
    ): List<ApiNinjasCity>
}
