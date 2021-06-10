package com.example.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationsEntity: LocationsEntity)

    @Query("SELECT * FROM locations_table ORDER BY id ASC")
    suspend fun readLocations(): MutableList<LocationsEntity>
}