package com.example.weather.repository

import com.example.weather.data.City
import com.example.weather.data.DailyForecast
import com.example.weather.data.HourlyForecast
import com.example.weather.data.WeatherInfo
import com.example.weather.data.getWeatherDescription
import com.example.weather.network.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class WeatherRepository {

    private val api = RetrofitModule.openMeteoApi

    suspend fun getWeatherForCity(city: City): Result<WeatherData> = withContext(Dispatchers.IO) {
        try {
            val response = api.getForecast(
                latitude = city.latitude,
                longitude = city.longitude,
                timezone = city.timezone
            )

            val currentWeather = response.currentWeather?.let { cw ->
                WeatherInfo(
                    temperature = cw.temperature,
                    weatherCode = cw.weathercode,
                    description = getWeatherDescription(cw.weathercode),
                    windSpeed = cw.windspeed
                )
            } ?: run {
                val idx = findCurrentHourIndex(response.hourly.time)
                WeatherInfo(
                    temperature = response.hourly.temperature.getOrElse(idx) { 0.0 },
                    weatherCode = response.hourly.weathercode.getOrElse(idx) { 0 },
                    description = getWeatherDescription(response.hourly.weathercode.getOrElse(idx) { 0 }),
                    humidity = response.hourly.humidity?.getOrNull(idx),
                    windSpeed = response.hourly.windSpeed?.getOrNull(idx),
                    precipitationProbability = response.hourly.precipitationProbability?.getOrNull(idx)
                )
            }

            val hourlyForecasts = response.hourly.time.take(24).mapIndexed { i, time ->
                val temp = response.hourly.temperature.getOrElse(i) { 0.0 }
                val code = response.hourly.weathercode.getOrElse(i) { 0 }
                HourlyForecast(
                    time = formatHour(time),
                    temperature = temp,
                    weatherCode = code,
                    description = getWeatherDescription(code)
                )
            }

            val dailyForecasts = response.daily.time.mapIndexed { i, date ->
                DailyForecast(
                    date = formatDate(date),
                    tempMax = response.daily.temperatureMax.getOrElse(i) { 0.0 },
                    tempMin = response.daily.temperatureMin.getOrElse(i) { 0.0 },
                    weatherCode = response.daily.weathercode.getOrElse(i) { 0 },
                    description = getWeatherDescription(response.daily.weathercode.getOrElse(i) { 0 }),
                    precipitationProbability = response.daily.precipitationProbabilityMax?.getOrNull(i)
                )
            }

            Result.success(
                WeatherData(
                    city = city,
                    current = currentWeather,
                    hourly = hourlyForecasts,
                    daily = dailyForecasts
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun findCurrentHourIndex(times: List<String>): Int {
        val now = LocalDateTime.now()
        for (i in times.indices) {
            try {
                val time = LocalDateTime.parse(times[i].replace("Z", "").take(19))
                if (time.isAfter(now) || time.isEqual(now)) return i.coerceAtLeast(0)
            } catch (_: Exception) {}
        }
        return 0
    }

    private fun formatHour(isoTime: String): String =
        if (isoTime.contains("T")) isoTime.substringAfter("T").take(5) else isoTime

    private fun formatDate(dateStr: String): String {
        return try {
            val date = java.time.LocalDate.parse(dateStr)
            date.format(DateTimeFormatter.ofPattern("EEE, d MMM", Locale("ru")))
        } catch (_: Exception) {
            dateStr
        }
    }
}

data class WeatherData(
    val city: City,
    val current: WeatherInfo,
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>
)
