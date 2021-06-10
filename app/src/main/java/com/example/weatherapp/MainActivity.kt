package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.weatherapp.abstraction.AbstractActivity
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AbstractActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun initLayout() {
        navController = Navigation.findNavController(this, R.id.nav_weather_fragment)
    }

    override fun runOperation() {
    }

    override fun stopOperation() {
    }

}