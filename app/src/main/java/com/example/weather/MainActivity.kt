package com.example.weather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.weather.ui.WeatherDetailFragment
import com.example.weather.ui.CityListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container, CityListFragment())
            }
        }
    }

    fun showWeatherDetail(cityId: Int) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container, WeatherDetailFragment.newInstance(cityId))
        }
    }
}