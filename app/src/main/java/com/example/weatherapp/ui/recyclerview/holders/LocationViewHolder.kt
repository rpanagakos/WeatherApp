package com.example.weatherapp.ui.recyclerview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.database.LocationsEntity
import kotlinx.android.synthetic.main.holder_location_item.view.*

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel, lastOne : Boolean, onClickElement:(selected : LocalModel) -> Unit) {
        when (data) {
            is LocationsEntity -> {
                itemView.locationText.text = data.location.substringBefore(",")
                itemView.countryText.text = data.location.substringAfter(", ")
                itemView.locationConstraint.setSafeOnClickListener {
                    onClickElement.invoke(data)
                }
            }
        }
    }
}