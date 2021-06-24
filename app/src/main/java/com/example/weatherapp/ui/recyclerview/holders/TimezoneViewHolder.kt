package com.example.weatherapp.ui.recyclerview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.models.time.TimeZone
import kotlinx.android.synthetic.main.holder_time_item.view.*

class TimezoneViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel) {
        when (data) {
            is  TimeZone-> {
                itemView.countryText.text = data.zone.substringBefore("/")
                itemView.locationText.text = data.zone.substringAfter("/")
                itemView.countryTime.text = data.localtime.substringAfter(" ")
            }
        }
    }
}