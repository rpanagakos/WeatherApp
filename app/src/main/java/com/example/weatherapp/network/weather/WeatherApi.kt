package com.example.weatherapp.network.weather

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.weather.WeatherResponse
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
        @Query("num_of_days") days: String
    ): Response<GenericResponse<WeatherResponse>>

    @GET("/premium/v1/weather.ashx?")
    suspend fun getDayDetails(
        @Query("q") city: String,
        @Query("format") format: String = "json",
        @Query("date") date: String
    ): Response<GenericResponse<WeatherResponse>>
}