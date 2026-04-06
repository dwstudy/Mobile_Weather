package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.FragmentCityListBinding
import com.example.weather.ui.adapters.CityAdapter
import com.example.weather.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.weather.MainActivity

class CityListFragment : Fragment() {

    private var _binding: FragmentCityListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCities()
    }

    private fun setupRecyclerView() {
        binding.citiesRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeCities() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cities.collectLatest { cities ->
                val adapter = CityAdapter(cities) { city ->
                    // Переход на детальный экран
                    (activity as? MainActivity)?.showWeatherDetail(city.id)
                }
                binding.citiesRecycler.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}