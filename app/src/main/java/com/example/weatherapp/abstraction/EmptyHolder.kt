package com.example.weatherapp.abstraction

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun presentData(data: LocalModel) {}
}