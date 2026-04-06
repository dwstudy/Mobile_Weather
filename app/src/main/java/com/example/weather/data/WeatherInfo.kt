package com.example.weather.data

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

//Погода определяется по статусу. Используем код и выводим текст в интерфейсе
fun getWeatherDescription(code: Int): String = when (code) {
    0 -> "Ясно"
    1 -> "Преимущественно ясно"
    2 -> "Переменная облачность"
    3 -> "Пасмурно"
    45 -> "Туман"
    48 -> "Изморозь"
    51, 53, 55 -> "Морось"
    56, 57 -> "Ледяная морось"
    61, 63, 65 -> "Дождь"
    66, 67 -> "Ледяной дождь"
    71, 73, 75 -> "Снег"
    77 -> "Снежные зёрна"
    80, 81, 82 -> "Ливень"
    85, 86 -> "Снежный ливень"
    95 -> "Гроза"
    96, 99 -> "Гроза с градом"
    else -> "Неизвестно"
}
