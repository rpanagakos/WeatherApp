package com.example.weatherapp.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weatherapp.network.ConstantsApi.Companion.LOCATIONS_TABLE

@Entity(tableName = LOCATIONS_TABLE, indices = [Index("location",name = "location", unique = true)])
class LocationsEntity(
    var location: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}