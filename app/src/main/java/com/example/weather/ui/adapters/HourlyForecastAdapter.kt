package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.HourlyForecast
import com.example.weather.databinding.ItemHourlyForecastBinding

class HourlyForecastAdapter(private val items: List<HourlyForecast>) :
    RecyclerView.Adapter<HourlyForecastAdapter.HourlyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding = ItemHourlyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = items[position]
        holder.binding.hourTime.text = item.time
        holder.binding.hourTemp.text = "${item.temperature.toInt()}°"
        holder.binding.hourDesc.text = item.description
    }

    override fun getItemCount() = items.size

    class HourlyViewHolder(val binding: ItemHourlyForecastBinding) : RecyclerView.ViewHolder(binding.root)
}