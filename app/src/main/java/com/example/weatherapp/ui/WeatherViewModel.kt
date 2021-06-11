package com.example.weatherapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.abstraction.SingleLiveEvent
import com.example.weatherapp.database.LocalDataSource
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.network.WeatherRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class WeatherViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRemoteRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    val latestLocation = SingleLiveEvent<String>()
    val internetConnection = SingleLiveEvent<Boolean>()
    val weatherResponse = SingleLiveEvent<WeatherResponse>()
    val weatherNextWeek = SingleLiveEvent<WeatherResponse>()
    val locations = SingleLiveEvent<MutableList<LocationsEntity>>()

    //Room database

    fun insertLocation(location: String, added: (Boolean) -> Unit) {
        val locationsEntity = LocationsEntity(location)
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localDataSource.insertLocation(locationsEntity)
            }.onSuccess {
                added.invoke(true)
            }.onFailure {
                handleFailures(it)
            }
        }
    }

    fun getLatestLocation(){
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localDataSource.getLatestLocation()
            }.onFailure {
                handleFailures(it)
            }.onSuccess {
                it?.let {
                    latestLocation.postValue( it.location)
                }
            }
        }
    }

    fun getAllLocations(){
        viewModelScope.launch(Dispatchers.Default){
            kotlin.runCatching {
                localDataSource.readLocations()
            }.onSuccess {
                locations.postValue(it)
            }.onFailure {
                handleFailures(it)
            }
        }
    }

    //Api calls
    fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                latestLocation.value?.let { weatherRepository.getCurrentWeather(city = it) }
            }.onFailure {
                //Show error handling message
                handleFailures(it)
            }.onSuccess { it ->
                when {
                    it == null -> {
                        //Show that something happened
                        handleFailures(null)
                    }
                    it.isSuccessful -> {
                        it.body()?.let {
                            it.data?.let {
                                weatherResponse.postValue(it)
                            }
                        }
                    }
                    else -> {
                        //Show error handling message
                        handleFailures(null)
                    }
                }
            }
        }
    }

    fun getNextWeek() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                latestLocation.value?.let { weatherRepository.getNextWeek(city = it, numOfDays = "8") }
            }.onFailure {
                //Show error handling message
                handleFailures(it)
            }.onSuccess { it ->
                when {
                    it == null -> {
                        //Show that something happened
                        handleFailures(null)
                    }
                    it.isSuccessful -> {
                        it.body()?.let {
                            it.data?.let {
                                weatherNextWeek.postValue(it)
                            }
                        }
                    }
                    else -> {
                        //Show error handling message
                        handleFailures(null)
                    }
                }
            }
        }
    }

    private fun handleFailures(throwable: Throwable?) {
        when (throwable) {
            null -> {
                internetConnection.postValue(false)
            }
            is SocketTimeoutException -> {
                internetConnection.postValue(true)

            }
            is UnknownHostException -> {
                internetConnection.postValue(true)
            }
            else -> {
                internetConnection.postValue(false)
            }
        }
    }

}