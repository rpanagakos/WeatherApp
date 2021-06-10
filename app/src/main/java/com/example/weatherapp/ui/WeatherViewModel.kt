package com.example.weatherapp.ui

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.weatherapp.abstraction.SingleLiveEvent
import com.example.weatherapp.database.LocalDataSource
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.network.WeatherRemoteRepository
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.format.SignStyle
import javax.inject.Inject

class WeatherViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRemoteRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    var cityName: String? = null
    val internetConnection = MutableLiveData<Boolean>()
    val weatherResponse = SingleLiveEvent<WeatherResponse>()
    val weatherNextWeek = SingleLiveEvent<WeatherResponse>()
    val locations = SingleLiveEvent<MutableList<LocationsEntity>>()


    //Room database
    fun offlineCache(location: String) {
        val locationsEntity = LocationsEntity(location)
        insertLocation(locationsEntity)
    }

    private fun insertLocation(locationsEntity: LocationsEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            localDataSource.insertLocation(locationsEntity)
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

    //Retrofit calls
    fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                cityName?.let { weatherRepository.getCurrentWeather(city = it) }
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
                cityName?.let { weatherRepository.getNextWeek(city = it, numOfDays = "8") }
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