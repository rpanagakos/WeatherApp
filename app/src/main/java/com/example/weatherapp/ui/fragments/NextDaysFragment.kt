package com.example.weatherapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.databinding.FragmentNextDaysBinding
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.recyclerview.WeatherAdapter

class NextDaysFragment : Fragment() {

    lateinit var binding: FragmentNextDaysBinding
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
        binding = FragmentNextDaysBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.daysWeatherRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.daysWeatherRecycler.showShimmer()
        initView()

        observeViewModel()
    }

    private fun initView() {

        viewModel.getNextWeek()

        binding.daysWeatherRecycler.adapter = adapter
        binding.backButton.setSafeOnClickListener {
            findNavController().navigate(R.id.action_nextDaysFragment_to_landingFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.weatherNextWeek.observe(viewLifecycleOwner, Observer {
            when{
                !it.weather.isNullOrEmpty() -> {
                    adapter.submitList(it.weather.drop(1))
                }
            }
            binding.daysWeatherRecycler.hideShimmer()
        })
    }
}