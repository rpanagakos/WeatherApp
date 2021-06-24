package com.example.weatherapp.models.weather

import com.example.weatherapp.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("avgtempC")
    val avgtempC: String,
    @SerializedName("avgtempF")
    val avgtempF: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
    @SerializedName("maxtempC")
    val maxtempC: String,
    @SerializedName("maxtempF")
    val maxtempF: String,
    @SerializedName("mintempC")
    val mintempC: String,
    @SerializedName("mintempF")
    val mintempF: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}