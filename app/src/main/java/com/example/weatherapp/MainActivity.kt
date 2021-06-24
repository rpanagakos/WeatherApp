package com.example.weatherapp

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.abstraction.AbstractActivity
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.fragments.DayDetailsFragment
import com.example.weatherapp.ui.fragments.LocationsFragment
import com.example.weatherapp.ui.fragments.LocationsFragmentDirections
import com.example.weatherapp.ui.fragments.NextDaysFragment
import com.example.weatherapp.ui.listeners.BottomNavListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AbstractActivity(R.layout.activity_main), BottomNavListener {

    private lateinit var navController: NavController

    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.locationsFragment, R.id.nextDaysFragment, R.id.dayDetailsFragment -> bottomNavigation.visibility =
                    View.GONE
                else -> bottomNavigation.visibility = View.VISIBLE
            }
        }

    override fun initLayout() {
        navController = Navigation.findNavController(this, R.id.nav_weather_fragment)
        bottomNavigation.setupWithNavController(navController)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.noLocation.observe(this, Observer {
            when (it) {
                true ->
                    bottomNavigation.visibility = View.GONE
                false ->
                    bottomNavigation.visibility = View.VISIBLE
            }
        })
    }

    override fun runOperation() {
        navController.addOnDestinationChangedListener(listener)
    }

    override fun stopOperation() {
        navController.removeOnDestinationChangedListener(listener)
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun motion(up: Boolean) {
    }

}