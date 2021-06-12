package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.databinding.FragmentNextDaysBinding
import com.example.weatherapp.models.Weather
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NextDaysFragment : AbstractFragment() {

    lateinit var binding: FragmentNextDaysBinding
    private val adapter: WeatherAdapter = WeatherAdapter() {
        (it as Weather).apply {
            findNavController().navigate(
                NextDaysFragmentDirections.actionNextDaysFragmentToDayDetailsFragment(
                    date = this.date
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNextDaysBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initLayout() {
        binding.daysWeatherRecycler.setHasFixedSize(true)
        binding.daysWeatherRecycler.adapter = adapter
        binding.daysWeatherRecycler.showShimmer()

        //for smoothest animation
        GlobalScope.launch(Dispatchers.Main) {
            delay(200)
            viewModel.getNextWeek()
        }
        binding.backButton.setSafeOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun observeViewModel() {
        viewModel.weatherNextWeek.observe(viewLifecycleOwner, Observer {
            when {
                !it.weather.isNullOrEmpty() -> {
                    adapter.submitList(it.weather.drop(1))
                }
            }
            binding.daysWeatherRecycler.hideShimmer()
        })
    }

    override fun stopOperations() {
    }
}