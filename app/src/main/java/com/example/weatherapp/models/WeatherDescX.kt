package com.example.weatherapp.models

import com.example.weatherapp.abstraction.LocalModel

data class WeatherDescX(
    val value: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}