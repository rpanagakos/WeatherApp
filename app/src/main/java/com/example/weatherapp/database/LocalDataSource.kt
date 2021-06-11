package com.example.weatherapp.database

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val locationsDao: LocationsDao
) {

    suspend fun readLocations(): MutableList<LocationsEntity> {
       return locationsDao.readLocations()
    }

    suspend fun insertLocation(locationsEntity: LocationsEntity){
        locationsDao.insertLocation(locationsEntity)
    }

    suspend fun getLatestLocation(): LocationsEntity {
        return locationsDao.getLatestLocation()
    }
}