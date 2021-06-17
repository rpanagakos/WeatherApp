package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.abstraction.SingleLiveEvent
import com.example.weatherapp.database.LocalDataSource
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.network.WeatherRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRemoteRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    val latestLocation = SingleLiveEvent<String>()
    val internetConnection = SingleLiveEvent<Boolean>()
    val weatherResponse = SingleLiveEvent<WeatherResponse>()
    val weatherNextWeek = SingleLiveEvent<WeatherResponse>()
    var locations: LiveData<MutableList<LocationsEntity>> = localDataSource.readLocations().asLiveData()
    var searchingLocations = SingleLiveEvent<MutableList<LocationsEntity>>()
    val dayDetails = SingleLiveEvent<WeatherResponse>()

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

    fun deleteLocation(locationsEntity: LocationsEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localDataSource.deleteLocation(locationsEntity)
            }.onFailure {
                handleFailures(it)
            }
        }
    }

    fun getLatestLocation() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localDataSource.getLatestLocation()
            }.onFailure {
                handleFailures(it)
            }.onSuccess {
                it?.let {
                    latestLocation.postValue(it.location)
                }
            }
        }
    }

    fun searchDatabase(location: String) {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                localDataSource.searchDatabase(location)
            }.onFailure {
                handleFailures(it)
            }.onSuccess {
                searchingLocations.postValue(it)
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
                latestLocation.value?.let {
                    weatherRepository.getNextWeek(
                        city = it,
                        numOfDays = "8"
                    )
                }
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

    fun getDayDetails(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                latestLocation.value?.let {
                    weatherRepository.getDayDetails(
                        city = it,
                        date = date
                    )
                }
            }.onFailure {
                handleFailures(it)
            }.onSuccess { it ->
                when {
                    it == null -> {
                        handleFailures(null)
                    }
                    it.isSuccessful -> {
                        it.body()?.let {
                            it.data?.let {
                                dayDetails.postValue(it)
                            }
                        }
                    }
                    else -> {
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