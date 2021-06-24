package com.example.weatherapp.network.time

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.time.TimeResponse
import retrofit2.Response
import javax.inject.Inject

class TimezoneRemoteRepositoryImpl @Inject constructor(
    private val timezoneApi: TimezoneApi
): TimezoneRemoteRepository{

    override suspend fun getTimezone(city: String): Response<GenericResponse<TimeResponse>> {
        return timezoneApi.getTimezone(city)
    }
}