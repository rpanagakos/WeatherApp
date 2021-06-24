package com.example.weatherapp.models.time

import com.example.weatherapp.models.Request
import com.google.gson.annotations.SerializedName

data class TimeResponse(
    @SerializedName("request")
    val request: List<Request>,
    @SerializedName("time_zone")
    val time_zone: List<TimeZone>
)