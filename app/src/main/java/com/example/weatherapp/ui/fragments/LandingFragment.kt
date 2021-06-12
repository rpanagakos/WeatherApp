package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.databinding.FragmentLandingBinding
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LandingFragment : AbstractFragment() {

    lateinit var binding: FragmentLandingBinding
    private val adapter: WeatherAdapter = WeatherAdapter() {}

    private val args: LandingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.generic = true
        return binding.root
    }

    override fun initLayout() {
        binding.hourlyWeatherRecycler.adapter = adapter
        binding.nextWeekTextView.setSafeOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_nextDaysFragment)
        }

        binding.addButton.setSafeOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_locationsFragment)
        }
    }

    override fun observeViewModel() {
        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            binding.weather = it
            when {
                !it.weather.isNullOrEmpty() -> {
                    viewModel.insertLocation(it.request[0].query) {}
                    adapter.submitList(it.weather[0].hourly)
                    binding.hourlyWeatherRecycler.hideShimmer()
                }
                else -> {
                    binding.animation.setAnimation("empty.json")
                    binding.genericText.text = getString(R.string.wrong_location)
                    binding.generic = true
                    binding.animation.playAnimation()
                }
            }
        })
        viewModel.latestLocation.observe(viewLifecycleOwner, Observer {
            binding.hourlyWeatherRecycler.showShimmer()
            binding.generic = false
            viewModel.getCurrentWeather()
        })
    }

    override fun stopOperations() {
    }

    override fun onResume() {
        super.onResume()
        when {
            !args.newLocation.equals("empty") -> viewModel.latestLocation.value = args.newLocation
            else -> viewModel.getLatestLocation()
        }
    }

}