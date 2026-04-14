package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.City
import com.example.weather.databinding.ItemCityBinding

class CityAdapter(
    private val cities: List<City>,
    private val onItemClick: (City) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.binding.cityName.text = city.name
        holder.binding.cityCoords.text = String.format("%.2f, %.2f", city.latitude, city.longitude)
        holder.itemView.setOnClickListener { onItemClick(city) }
    }

    override fun getItemCount() = cities.size

    class CityViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)
}