package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.DailyForecast
import com.example.weather.databinding.ItemDailyForecastBinding

class DailyForecastAdapter(private val items: List<DailyForecast>) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = ItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = items[position]
        holder.binding.dayDate.text = item.date
        holder.binding.dayTempMax.text = "${item.tempMax.toInt()}°"
        holder.binding.dayTempMin.text = "${item.tempMin.toInt()}°"
        holder.binding.dayDesc.text = item.description
    }

    override fun getItemCount() = items.size

    class DailyViewHolder(val binding: ItemDailyForecastBinding) : RecyclerView.ViewHolder(binding.root)
}