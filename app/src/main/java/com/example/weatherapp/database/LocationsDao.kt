package com.example.weatherapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationsEntity: LocationsEntity)

    @Query("SELECT * FROM locations_table ORDER BY id DESC")
    fun readLocations(): Flow<MutableList<LocationsEntity>>

    @Query("SELECT * FROM locations_table ORDER BY  id DESC LIMIT 1")
    suspend fun getLatestLocation(): LocationsEntity

    @Delete
    suspend fun deleteLocation(locationsEntity: LocationsEntity)
}