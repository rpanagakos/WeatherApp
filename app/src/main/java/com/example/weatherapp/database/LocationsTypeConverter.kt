package com.example.weatherapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun locationsToString(locations : String): String{
        return gson.toJson(locations)
    }

    @TypeConverter
    fun stringToList(data: String): MutableList<String>{
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}