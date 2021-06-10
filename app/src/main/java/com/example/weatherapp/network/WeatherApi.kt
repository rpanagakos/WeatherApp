package com.example.weatherapp.network

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.network.ConstantsApi.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/premium/v1/weather.ashx?")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("format") format: String = "json",
        @Query("date") date: String = "today"
    ): Response<GenericResponse<WeatherResponse>>

    @GET("/premium/v1/weather.ashx?")
    suspend fun getNextWeek(
        @Query("q") city: String,
        @Query("format") format: String = "json",
        @Query("num_of_days") date: String
    ): Response<GenericResponse<WeatherResponse>>
}