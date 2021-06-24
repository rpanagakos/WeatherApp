package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.abstraction.SingleLiveEvent
import com.example.weatherapp.database.LocalDataSource
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.models.time.TimeResponse
import com.example.weatherapp.network.time.TimezoneRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class TimezoneViewModel  @Inject constructor(
    private val timezoneRemoteRepository: TimezoneRemoteRepository,
    private val localDataSource: LocalDataSource
): ViewModel(){

    var timeLocations: LiveData<MutableList<LocationsEntity>> =
        localDataSource.readLocations().asLiveData()
    val timeZoneList = SingleLiveEvent<MutableList<TimeResponse>>()
    val internetConnection = SingleLiveEvent<Boolean>()

    fun getTimezoneLocations(){
        viewModelScope.launch(Dispatchers.Default) {
           val initLocation = mutableListOf<TimeResponse>()
            coroutineScope {
                timeLocations.value?.forEach { city ->
                    launch {
                        kotlin.runCatching {
                            timezoneRemoteRepository.getTimezone(city.location)
                        }.onSuccess {
                            when {
                                it == null -> {
                                    handleFailures(null)
                                }
                                it.isSuccessful -> {
                                    it.body()?.let {
                                        it.data?.let {
                                            initLocation.add(it)
                                        }
                                    }
                                }
                                else -> {
                                    handleFailures(null)
                                }
                            }
                        }.onFailure {
                            handleFailures(it)
                        }
                    }
                }
            }
            timeZoneList.postValue(initLocation)
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