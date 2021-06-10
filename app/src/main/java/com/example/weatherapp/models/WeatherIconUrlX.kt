package com.example.weatherapp.models

import com.example.weatherapp.abstraction.LocalModel

data class WeatherIconUrlX(
    val value: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}