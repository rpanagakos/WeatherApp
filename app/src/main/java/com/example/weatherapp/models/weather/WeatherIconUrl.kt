package com.example.weatherapp.models.weather

import com.example.weatherapp.abstraction.LocalModel

data class WeatherIconUrl(
    val value: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}