package com.example.weatherapp.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.Utils.dateToFullDateName
import de.hdodenhof.circleimageview.CircleImageView

object WeatherBinding {

    @BindingAdapter(value = ["imageURLWeather"])
    @JvmStatic
    fun CircleImageView.loadImageFromUrl(imageUrl: String?){
        if (imageUrl != null){
            load(imageUrl) {
                crossfade(600)
                placeholder(R.drawable.ic_failed)
                error(R.drawable.ic_failed)
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