package com.example.weatherapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.ui.TimezoneViewModel


class TimeFragment : AbstractFragment() {

    lateinit var timeViewModel: TimezoneViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        timeViewModel = ViewModelProvider(requireActivity()).get(TimezoneViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    override fun initLayout() {

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
                }
            }
        })
    }

    override fun stopOperations() {
    }

}