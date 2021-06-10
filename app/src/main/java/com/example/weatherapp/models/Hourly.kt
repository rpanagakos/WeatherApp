package com.example.weatherapp.models

import com.example.weatherapp.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("tempC")
    val tempC: String,
    @SerializedName("tempF")
    val tempF: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("weatherCode")
    val weatherCode: String,
    @SerializedName("weatherDesc")
    val weatherDesc: List<WeatherDescX>,
    @SerializedName("weatherIconUrl")
    val weatherIconUrl: List<WeatherIconUrlX>
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}