package com.example.weatherapp.network

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.WeatherResponse
import retrofit2.Response
import javax.inject.Inject


class WeatherRemoteRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRemoteRepository {

    override suspend fun getCurrentWeather(city: String): Response<GenericResponse<WeatherResponse>> {
        return weatherApi.getCurrentWeather(city)
    }

    override suspend fun getNextWeek(city: String, numOfDays : String): Response<GenericResponse<WeatherResponse>> {
        return weatherApi.getNextWeek(city, date = numOfDays)
    }
}