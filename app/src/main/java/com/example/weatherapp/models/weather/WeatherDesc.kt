package com.example.weatherapp.models.weather

import com.example.weatherapp.abstraction.LocalModel

data class WeatherDesc(
    val value: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}