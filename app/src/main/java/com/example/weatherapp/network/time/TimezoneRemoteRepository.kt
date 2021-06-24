package com.example.weatherapp.network.time

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.time.TimeResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response

@ActivityRetainedScoped
interface TimezoneRemoteRepository {

    suspend fun getTimezone(city: String): Response<GenericResponse<TimeResponse>>

}