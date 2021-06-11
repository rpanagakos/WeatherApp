package com.example.weatherapp.ui.recyclerview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.database.LocationsEntity
import kotlinx.android.synthetic.main.holder_location_item.view.*

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel, lastOne : Boolean) {
        when (data) {
            is LocationsEntity -> {
                itemView.view.visibility = if (lastOne) View.GONE else View.VISIBLE
                itemView.locationText.text = data.location.substringBefore(",")
                itemView.countryText.text = data.location.substringAfter(", ")
            }
        }
    }
}