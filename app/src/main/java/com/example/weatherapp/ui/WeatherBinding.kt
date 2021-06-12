package com.example.weatherapp.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.Utils.dateToFullDateName
import de.hdodenhof.circleimageview.CircleImageView

object WeatherBinding {

    @BindingAdapter(value = ["imageURLWeather"])
    @JvmStatic
    fun CircleImageView.loadImageFromUrl(imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .error(Glide.with(this).load(R.drawable.ic_failed))
                .circleCrop()
                .into(this)
        }

    }

    @BindingAdapter(value = ["dateFormatWeather"])
    @JvmStatic
    fun TextView.changeDateWeatherFormat(date: String?) {
        if (date != null)
            text = dateToFullDateName(date)
    }


}