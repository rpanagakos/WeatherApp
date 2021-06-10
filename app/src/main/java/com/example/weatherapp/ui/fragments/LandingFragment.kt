package com.example.weatherapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.Utils.hideKeyboard
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.databinding.FragmentLandingBinding
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LandingFragment : Fragment() {

    lateinit var binding: FragmentLandingBinding
    private val adapter: WeatherAdapter = WeatherAdapter()

    private lateinit var viewModel: WeatherViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.searchMode = true
        binding.generic = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.hourlyWeatherRecycler.adapter = adapter
        binding.nextWeekTextView.setSafeOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_nextDaysFragment)
        }

        binding.searchButtom.setSafeOnClickListener {

            when {
                !binding.searchEditText.text.isNullOrEmpty() -> {
                    //make the call to API
                    viewModel.cityName = binding.searchEditText.text.toString()
                    binding.searchEditText.setText("")
                    viewModel.getCurrentWeather()
                    hideKeyboard()
                    binding.hourlyWeatherRecycler.visibility = View.VISIBLE
                    binding.hourlyWeatherRecycler.showShimmer()
                }
                else -> {
                    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    binding.searchEditText.requestFocus()
                    binding.searchMode = true
                    binding.generic = false
                }
            }

        }
    }

    private fun observeViewModel() {
        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            binding.weather = it
            when{
                !it.weather.isNullOrEmpty() -> {
                    binding.searchMode = false
                    binding.generic = false
                    adapter.submitList(it.weather[0].hourly)
                    binding.hourlyWeatherRecycler.hideShimmer()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        when{
            !viewModel.cityName.isNullOrEmpty() -> {
                //make the call to API
                viewModel.getCurrentWeather()
                binding.searchMode = false
                hideKeyboard()
                binding.generic = false
            }
        }
    }

}