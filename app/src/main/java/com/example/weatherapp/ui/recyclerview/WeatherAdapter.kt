package com.example.weatherapp.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.DiffUtilClass
import com.example.weatherapp.abstraction.EmptyHolder
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.models.Hourly
import com.example.weatherapp.models.Weather
import com.example.weatherapp.ui.recyclerview.holders.DaysViewHolder
import com.example.weatherapp.ui.recyclerview.holders.HourlyViewHolder
import com.example.weatherapp.ui.recyclerview.holders.LocationViewHolder

class WeatherAdapter(private val onClickElement: (selected: LocalModel) -> Unit) :
    ListAdapter<LocalModel, RecyclerView.ViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.holder_days_item -> DaysViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            R.layout.holder_hourly_weather_item -> HourlyViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            R.layout.holder_location_item -> LocationViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            else -> EmptyHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is DaysViewHolder -> holder.present(getItem(position), onClickElement)
        is HourlyViewHolder -> holder.present(getItem(position))
        is LocationViewHolder -> holder.present(
            getItem(position),
            isLast(position, itemCount),
            onClickElement
        )
        else -> Unit
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is Hourly -> R.layout.holder_hourly_weather_item
        is Weather -> R.layout.holder_days_item
        is LocationsEntity -> R.layout.holder_location_item
        else -> R.layout.holder_days_item
    }

    private fun isLast(itemPosition: Int, items: Int): Boolean {
        return itemPosition == items - 1
    }
}