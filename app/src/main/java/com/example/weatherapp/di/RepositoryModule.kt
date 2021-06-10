package com.example.weatherapp.di

import com.example.weatherapp.network.WeatherRemoteRepository
import com.example.weatherapp.network.WeatherRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWeatherRemoteRepository(weatherRemoteRepositoryImpl: WeatherRemoteRepositoryImpl): WeatherRemoteRepository

}