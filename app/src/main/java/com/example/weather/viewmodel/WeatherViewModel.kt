package com.example.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.City
import com.example.weather.data.CITIES
import com.example.weather.repository.WeatherData
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _cities = MutableStateFlow(CITIES)
    val cities: StateFlow<List<City>> = _cities.asStateFlow()

    private val _selectedCityWeather = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val selectedCityWeather: StateFlow<WeatherUiState> = _selectedCityWeather.asStateFlow()

    private val _cityWeathersCache = MutableStateFlow<Map<Int, WeatherData>>(emptyMap())
    val cityWeathersCache: StateFlow<Map<Int, WeatherData>> = _cityWeathersCache.asStateFlow()

    fun loadWeatherForCity(city: City) {
        viewModelScope.launch {
            _selectedCityWeather.value = WeatherUiState.Loading
            repository.getWeatherForCity(city)
                .onSuccess { data ->
                    _selectedCityWeather.value = WeatherUiState.Success(data)
                    _cityWeathersCache.value = _cityWeathersCache.value + (city.id to data)
                }
                .onFailure { e ->
                    _selectedCityWeather.value = WeatherUiState.Error(e.message ?: "Ошибка загрузки")
                }
        }
    }

    fun loadWeatherForAllCities() {
        viewModelScope.launch {
            val results = mutableMapOf<Int, WeatherData>()
            cities.value.forEach { city ->
                repository.getWeatherForCity(city)
                    .onSuccess { data -> results[city.id] = data }
                    .onFailure { }
            }
            _cityWeathersCache.value = results
        }
    }

    fun refreshWeather(city: City) {
        loadWeatherForCity(city)
    }
}

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(val data: WeatherData) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}
