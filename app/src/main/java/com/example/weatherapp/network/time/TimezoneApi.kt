package com.example.weatherapp.network.time

import com.example.weatherapp.models.GenericResponse
import com.example.weatherapp.models.time.TimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TimezoneApi {

    @GET("/premium/v1/tz.ashx?")
    suspend fun getTimezone(
        @Query("q") city: String,
        @Query("format") format: String = "json"
    ) : Response<GenericResponse<TimeResponse>>
}