package com.example.weatherapp.ui.recyclerview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.getHourlyTime
import com.example.weatherapp.models.Hourly
import com.example.weatherapp.ui.WeatherBinding.loadImageFromUrl
import kotlinx.android.synthetic.main.holder_day_details_item.view.*

class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel) {
        when (data) {
            is Hourly -> {
                itemView.degreesText.text = data.tempC + "º"
                itemView.weatherImage.loadImageFromUrl(data.weatherIconUrl[0].value)
                itemView.weatherDesc.text = data.weatherDesc[0].value
                itemView.timeText.text = getHourlyTime(data.time)
            }
        }
    }
}