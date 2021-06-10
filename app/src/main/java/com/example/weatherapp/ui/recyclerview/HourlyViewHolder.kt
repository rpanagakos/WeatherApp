package com.example.weatherapp.ui.recyclerview

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.dateHHMM
import com.example.weatherapp.models.Hourly
import com.example.weatherapp.ui.WeatherBinding.loadImageFromUrl
import kotlinx.android.synthetic.main.holder_hourly_weather_item.view.*

class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel) {
        when (data) {
            is Hourly -> {
                itemView.weatherImage.loadImageFromUrl(data.weatherIconUrl[0].value)
                when (data.time) {
                    "0" -> {
                        itemView.timeNtemp.text = "00:00 AM" + "\n" + data.tempC + "º"
                    }
                    else -> {
                        itemView.timeNtemp.text = dateHHMM(data.time) + "\n" + data.tempC + "º"
                    }
                }
            }
        }
    }
}