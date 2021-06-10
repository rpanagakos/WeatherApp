package com.example.weatherapp.models

import com.example.weatherapp.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("query")
    val query: String,
    @SerializedName("type")
    val type: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}