package com.example.weatherapp.di

import com.example.weatherapp.network.time.TimezoneRemoteRepository
import com.example.weatherapp.network.time.TimezoneRemoteRepositoryImpl
import com.example.weatherapp.network.weather.WeatherRemoteRepository
import com.example.weatherapp.network.weather.WeatherRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWeatherRemoteRepository(weatherRemoteRepositoryImpl: WeatherRemoteRepositoryImpl): WeatherRemoteRepository

    @Binds
    abstract fun provideTimezoneRemoteRepository(timezoneRemoteRepositoryImpl: TimezoneRemoteRepositoryImpl): TimezoneRemoteRepository

}