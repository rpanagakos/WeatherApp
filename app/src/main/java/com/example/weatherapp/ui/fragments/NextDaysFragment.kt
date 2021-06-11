package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.databinding.FragmentNextDaysBinding
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NextDaysFragment : AbstractFragment() {

    lateinit var binding: FragmentNextDaysBinding
    private val adapter: WeatherAdapter = WeatherAdapter(){}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNextDaysBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initLayout() {
        viewModel.getNextWeek()

        binding.daysWeatherRecycler.adapter = adapter
        binding.daysWeatherRecycler.showShimmer()
        binding.backButton.setSafeOnClickListener {
            findNavController().navigate(R.id.action_nextDaysFragment_to_landingFragment)
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