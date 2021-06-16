package com.example.weatherapp.network

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.WeatherResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response

@ActivityRetainedScoped
interface WeatherRemoteRepository {

    suspend fun getCurrentWeather(city: String): Response<GenericResponse<WeatherResponse>>

    suspend fun getNextWeek(city: String, numOfDays : String): Response<GenericResponse<WeatherResponse>>

    suspend fun getDayDetails(city: String, date : String): Response<GenericResponse<WeatherResponse>>
}