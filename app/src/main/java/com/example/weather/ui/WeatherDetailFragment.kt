package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.data.CITIES
import com.example.weather.databinding.FragmentWeatherDetailBinding
import com.example.weather.network.RetrofitModule
import com.example.weather.repository.WeatherRepository
import com.example.weather.repository.WeatherViewModelFactory
import com.example.weather.ui.adapters.DailyForecastAdapter
import com.example.weather.ui.adapters.HourlyForecastAdapter
import com.example.weather.viewmodel.WeatherViewModel
import com.example.weather.viewmodel.WeatherUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels {
        val api = RetrofitModule.openMeteoApi
        val context = requireContext().applicationContext
        val repository = WeatherRepository(api, context)
        WeatherViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем cityId из аргументов навигации
        val cityId = arguments?.getInt("cityId") ?: -1
        val city = CITIES.find { it.id == cityId }

        setupRecyclers()
        observeWeather()

        if (city != null) {
            viewModel.loadWeatherForCity(city)
        } else {
            showError(getString(R.string.city_not_found))
        }
    }

    private fun setupRecyclers() {
        binding.hourlyRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.dailyRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeWeather() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedCityWeather.collectLatest { state ->
                when (state) {
                    is WeatherUiState.Loading -> showLoading(true)
                    is WeatherUiState.Success -> {
                        showLoading(false)
                        displayWeatherData(state.data)
                    }
                    is WeatherUiState.Error -> {
                        showLoading(false)
                        showError(state.message)
                    }
                }
            }
        }
    }

    private fun displayWeatherData(data: com.example.weather.repository.WeatherData) {
        with(binding) {
            currentTemp.text = "${data.current.temperature.toInt()}°C"
            currentDesc.text = data.current.description
            humidity.text = "Влажность: ${data.current.humidity ?: "--"}%"
            wind.text = "Ветер: ${data.current.windSpeed ?: "--"} м/с"
            precipProb.text = "Осадки: ${data.current.precipitationProbability ?: "--"}%"

            hourlyRecycler.adapter = HourlyForecastAdapter(data.hourly)
            dailyRecycler.adapter = DailyForecastAdapter(data.daily)
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        binding.errorText.text = message
        binding.errorText.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}