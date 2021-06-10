package com.example.weatherapp.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.Utils.dateToFullDateName

object WeatherBinding {

    @BindingAdapter(value = ["imageURLWeather"])
    @JvmStatic
    fun ImageView.loadImageFromUrl(imageUrl: String?){
        if (imageUrl != null){
            load(imageUrl) {
                crossfade(500)
                placeholder(R.mipmap.ic_launcher)
                error(R.mipmap.ic_launcher)
            }
        }
    }

    @BindingAdapter(value = ["dateFormatWeather"])
    @JvmStatic
    fun TextView.changeDateWeatherFormat(date: String?){
        if (date != null)
            text = dateToFullDateName(date)
    }


}