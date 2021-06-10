package com.example.weatherapp.database

import android.content.Context
import androidx.room.*

@Database(
    entities = [LocationsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocationsTypeConverter::class)
abstract class LocationsDatabase : RoomDatabase() {

    abstract fun locationsDao(): LocationsDao

}