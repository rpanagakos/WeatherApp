package com.example.weatherapp

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.weatherapp.abstraction.AbstractActivity
import com.example.weatherapp.ui.fragments.DayDetailsFragment
import com.example.weatherapp.ui.fragments.LocationsFragment
import com.example.weatherapp.ui.fragments.LocationsFragmentDirections
import com.example.weatherapp.ui.fragments.NextDaysFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

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

    override fun onBackPressed() {
        when (nav_weather_fragment.childFragmentManager.fragments[0]) {
            is DayDetailsFragment, is NextDaysFragment -> super.onBackPressed()
            is LocationsFragment -> navController.navigate(
                LocationsFragmentDirections.actionLocationsFragmentToLandingFragment(
                    "empty"
                )
            )
            else -> finish()
        }
    }

}