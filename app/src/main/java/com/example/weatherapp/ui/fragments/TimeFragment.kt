package com.example.weatherapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.databinding.FragmentTimeBinding
import com.example.weatherapp.ui.TimezoneViewModel
import com.example.weatherapp.ui.recyclerview.adapters.TimezoneAdapter


class TimeFragment : AbstractFragment() {

    lateinit var timeViewModel: TimezoneViewModel
    lateinit var binding: FragmentTimeBinding

    private val adapter : TimezoneAdapter = TimezoneAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timeViewModel = ViewModelProvider(requireActivity()).get(TimezoneViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTimeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initLayout() {
        binding.timeRecyclerview.adapter = adapter
    }

    override fun observeViewModel() {
        timeViewModel.timeLocations.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    timeViewModel.getTimezoneLocations()
                }
            }
        })

        timeViewModel.timeZoneList.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    adapter.submitList(it as List<LocalModel>?)
                }
            }
        })
    }

    override fun stopOperations() {
    }

}