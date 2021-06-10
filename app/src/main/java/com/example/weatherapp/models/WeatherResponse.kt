package com.example.weatherapp.models

import com.example.weatherapp.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current_condition")
    val current_condition: List<CurrentCondition>,
    @SerializedName("request")
    val request: List<Request>,
    @SerializedName("weather")
    val weather: List<Weather>
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}