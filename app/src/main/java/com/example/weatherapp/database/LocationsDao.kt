package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationsEntity: LocationsEntity)

    @Query("SELECT * FROM locations_table ORDER BY id ASC")
    suspend fun readLocations(): MutableList<LocationsEntity>

    @Query("SELECT * FROM locations_table ORDER BY  id DESC LIMIT 1")
    suspend fun getLatestLocation(): LocationsEntity
}