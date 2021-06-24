package com.example.weatherapp.models.time

import com.example.weatherapp.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class TimeZone(
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("utcOffset")
    val utcOffset: String,
    @SerializedName("zone")
    val zone: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false

}