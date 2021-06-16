package com.example.weatherapp.abstraction

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.WeatherViewModel

abstract class AbstractFragment : Fragment() {

    lateinit var viewModel: WeatherViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModel()

        viewModel.internetConnection.observe(viewLifecycleOwner, Observer {
            when(it){
                true -> Toast.makeText(requireActivity(), "Check your internet connection.", Toast.LENGTH_LONG).show()
                false -> Toast.makeText(requireActivity(), "Something went wrong. Try again later", Toast.LENGTH_LONG).show()
            }
        })
    }

    abstract fun initLayout()

    abstract fun observeViewModel()

    override fun onPause() {
        super.onPause()
        stopOperations()
    }

    abstract fun stopOperations()
}