package com.example.weatherapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MainActivity
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.databinding.FragmentTimeBinding
import com.example.weatherapp.ui.TimezoneViewModel
import com.example.weatherapp.ui.listeners.BottomNavListener
import com.example.weatherapp.ui.recyclerview.adapters.TimezoneAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeFragment : AbstractFragment() {

    lateinit var timeViewModel: TimezoneViewModel
    lateinit var binding: FragmentTimeBinding
    private var listener :  BottomNavListener? = null

    private val adapter : TimezoneAdapter = TimezoneAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (requireActivity() as MainActivity)
        timeViewModel = ViewModelProvider(requireActivity()).get(TimezoneViewModel::class.java)
        listener = (requireActivity() as MainActivity)
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
        binding.timeRecyclerview.setHasFixedSize(true)
        binding.timeRecyclerview.adapter = adapter
        binding.timeRecyclerview.showShimmer()

        binding.timeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when{
                    dy > 0 -> {listener?.motion(true)}
                    else -> { listener?.motion(false) }
                }
            }
        })
    }

    override fun observeViewModel() {
        viewModel.locations.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    timeViewModel.getTimezoneLocations(it)
                }
            }
        })

        timeViewModel.timeZoneList.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    adapter.submitList(it as List<LocalModel>?)
                    binding.timeRecyclerview.hideShimmer()
                }
            }
        })
    }

    override fun stopOperations() {
        listener = null
    }

}