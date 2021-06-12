package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.Utils.dateToDayNameEEEE
import com.example.weatherapp.databinding.FragmentDayDetailsBinding
import com.example.weatherapp.ui.recyclerview.DetailsAdapter
import kotlinx.android.synthetic.main.fragment_day_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DayDetailsFragment : AbstractFragment() {

    lateinit var binding : FragmentDayDetailsBinding
    private val adapter: DetailsAdapter = DetailsAdapter()
    private val args : DayDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDayDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun initLayout() {
        binding.dayDetailsRecycler.layoutManager =  GridLayoutManager(context, 2)
        dayDetailsRecycler.setHasFixedSize(true)
        binding.dayDetailsRecycler.adapter = adapter
        binding.dayDetailsRecycler.showShimmer()

        //for smoothest animation
        GlobalScope.launch(Dispatchers.Main){
            delay(200)
            viewModel.getDayDetails(args.date)
        }
        binding.dayText.text = dateToDayNameEEEE(args.date)

        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun observeViewModel() {
        viewModel.dayDetails.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.weather[0].hourly)
            binding.dayDetailsRecycler.hideShimmer()
        })
    }

    override fun stopOperations() {
    }

}