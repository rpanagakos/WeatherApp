package com.example.weatherapp.ui.recyclerview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.getHourlyTime
import com.example.weatherapp.models.weather.Hourly
import com.example.weatherapp.ui.WeatherBinding.loadImageFromUrl
import kotlinx.android.synthetic.main.holder_hourly_weather_item.view.*

class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel) {
        when (data) {
            is Hourly -> {
                itemView.weatherImage.loadImageFromUrl(data.weatherIconUrl[0].value)
                itemView.timeNtemp.text = getHourlyTime(data.time)
                itemView.tempHourly.text = data.tempC + "º"
            }
        }
    }
}