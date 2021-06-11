package com.example.weatherapp.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.network.ConstantsApi.Companion.LOCATIONS_TABLE

@Entity(tableName = LOCATIONS_TABLE, indices = [Index("location",name = "location", unique = true)])
data class LocationsEntity(
    var location: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}