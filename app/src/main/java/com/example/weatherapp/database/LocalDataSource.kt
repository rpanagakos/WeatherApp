package com.example.weatherapp.database

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val locationsDao: LocationsDao
) {

    fun readLocations(): MutableList<LocationsEntity> {
       return locationsDao.readLocations()
    }

    suspend fun insertLocation(locationsEntity: LocationsEntity){
        locationsDao.insertLocation(locationsEntity)
    }
}