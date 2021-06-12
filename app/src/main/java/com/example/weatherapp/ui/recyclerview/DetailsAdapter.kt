package com.example.weatherapp.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.DiffUtilClass
import com.example.weatherapp.abstraction.EmptyHolder
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.models.Hourly
import com.example.weatherapp.ui.recyclerview.holders.DetailsViewHolder

class DetailsAdapter(): ListAdapter<LocalModel, RecyclerView.ViewHolder>(DiffUtilClass<LocalModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            R.layout.holder_day_details_item -> DetailsViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            else -> EmptyHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when(holder) {
        is DetailsViewHolder -> holder.present(getItem(position))
        else -> Unit
    }

    override fun getItemViewType(position: Int) = when (getItem(position)){
        is Hourly -> R.layout.holder_day_details_item
        else -> R.layout.holder_day_details_item
    }
}