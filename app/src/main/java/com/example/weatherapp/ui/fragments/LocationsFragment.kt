package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.hideKeyboard
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.abstraction.Utils.showKeyboard
import com.example.weatherapp.databinding.FragmentLocationsBinding
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : AbstractFragment() {

    lateinit var binding: FragmentLocationsBinding
    private val adapter: WeatherAdapter = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initLayout() {
        binding.locationsRecycler.adapter = adapter
        viewModel.getAllLocations()
        binding.searchEditText.showKeyboard()
        binding.backButton.setOnClickListener {
            hideKeyboard()
            findNavController().navigate(R.id.action_locationsFragment_to_landingFragment)
        }

        binding.searchButtom.setSafeOnClickListener {
            navigateToLanding()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                navigateToLanding()
            }
            true
        }
    }

    override fun observeViewModel() {
        viewModel.locations.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    binding.cardView.visibility = View.VISIBLE
                    adapter.submitList(it as List<LocalModel>?)
                }
                else -> {
                    binding.newLocationText.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun stopOperations() {
    }

    private fun navigateToLanding() {
        when {
            !binding.searchEditText.text.isNullOrEmpty() -> {
                hideKeyboard()
                findNavController().navigate(
                    LocationsFragmentDirections.actionLocationsFragmentToLandingFragment(
                        binding.searchEditText.text.toString()
                    )
                )
            }
        }
    }
}