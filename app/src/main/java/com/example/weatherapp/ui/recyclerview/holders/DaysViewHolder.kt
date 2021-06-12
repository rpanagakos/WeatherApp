package com.example.weatherapp.ui.recyclerview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.dateToDayNameEEEE
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.models.Weather
import com.example.weatherapp.ui.WeatherBinding.loadImageFromUrl
import kotlinx.android.synthetic.main.holder_days_item.view.*

class DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun present(data: LocalModel, onClickElement:(selected : LocalModel) -> Unit){
        when(data){
            is Weather -> {
                itemView.dayText.text = dateToDayNameEEEE(data.date)
                itemView.weatherType.text = data.hourly[6].weatherDesc[0].value
                itemView.weatherImage.loadImageFromUrl(data.hourly[6].weatherIconUrl[0].value)
                itemView.dayConstraint.setSafeOnClickListener {
                    onClickElement.invoke(data)
                }
            }
        }
    }
}