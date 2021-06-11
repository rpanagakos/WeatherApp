package com.example.weatherapp.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val locationsDao: LocationsDao
) {

    fun readLocations(): Flow<MutableList<LocationsEntity>> {
       return locationsDao.readLocations()
    }

    suspend fun insertLocation(locationsEntity: LocationsEntity){
        locationsDao.insertLocation(locationsEntity)
    }

    suspend fun getLatestLocation(): LocationsEntity {
        return locationsDao.getLatestLocation()
    }

    suspend fun deleteLocation(locationsEntity: LocationsEntity){
        return locationsDao.deleteLocation(locationsEntity)
    }
}