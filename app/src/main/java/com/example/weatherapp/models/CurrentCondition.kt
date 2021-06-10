package com.example.weatherapp.models

import com.example.weatherapp.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class CurrentCondition(
    @SerializedName("FeelsLikeC")
    val FeelsLikeC: String,
    @SerializedName("FeelsLikeF")
    val FeelsLikeF: String,
    @SerializedName("cloudcover")
    val cloudcover: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("observation_time")
    val observation_time: String,
    @SerializedName("temp_C")
    val temp_C: String,
    @SerializedName("temp_F")
    val temp_F: String,
    @SerializedName("weatherDesc")
    val weatherDesc: List<WeatherDesc>,
    @SerializedName("weatherIconUrl")
    val weatherIconUrl: List<WeatherIconUrl>
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}