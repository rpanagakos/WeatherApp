package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.hideKeyboard
import com.example.weatherapp.abstraction.Utils.searchQuery
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.abstraction.Utils.showKeyboard
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.databinding.FragmentLocationsBinding
import com.example.weatherapp.ui.recyclerview.SwipeToDelete
import com.example.weatherapp.ui.recyclerview.adapters.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class LocationsFragment : AbstractFragment() {


    lateinit var binding: FragmentLocationsBinding

    private val adapter: WeatherAdapter = WeatherAdapter() {
        (it as LocationsEntity).apply {
            binding.searchEditText.setText(this.location)
            navigateToLanding(this.location)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun initLayout() {
        binding.locationsRecycler.adapter = adapter
        swipeToDelete(binding.locationsRecycler)
        binding.searchEditText.showKeyboard()
        binding.backButton.setOnClickListener {
            hideKeyboard()
            activity?.onBackPressed()
        }

        binding.searchButtom.setSafeOnClickListener {
            navigateToLanding(binding.searchEditText.text.toString())
        }

        binding.animation.setSafeOnClickListener {
            binding.animation.playAnimation()
        }

        binding.searchEditText.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    navigateToLanding(binding.searchEditText.text.toString())
                }
                true
            }

            searchQuery()
                .debounce(600)
                .onEach {
                    when (binding.searchEditText.text.isNullOrEmpty()) {
                        false -> {
                            binding.searchEditText.text?.let {
                                viewModel.searchDatabase("%${binding.searchEditText.text.toString()}%")
                            }
                        }
                        else -> {
                            adapter.submitList(viewModel.locations.value as List<LocalModel>?)
                        }
                    }
                }
                .launchIn(lifecycleScope)
        }
    }

    override fun observeViewModel() {
        viewModel.locations.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    binding.noLocations = false
                    adapter.submitList(it as List<LocalModel>?)
                }
                else -> {
                    binding.noLocations = true
                    binding.animation.playAnimation()
                }
            }
        })

        viewModel.searchingLocations.observe(viewLifecycleOwner, Observer {
            when {
                it.size > 0 -> {
                    adapter.submitList(it as List<LocalModel>?)
                }
            }
        })
    }

    override fun stopOperations() {
    }

    private fun navigateToLanding(area: String = "") {
        when {
            !area.isEmpty() -> {
                if (binding.noLocations == true) binding.animation.cancelAnimation()
                hideKeyboard()
                findNavController().navigate(
                    LocationsFragmentDirections.actionLocationsFragmentToLandingFragment(
                        area
                    )
                )
            }
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete =
                    adapter.currentList[viewHolder.absoluteAdapterPosition] as LocationsEntity
                viewModel.deleteLocation(itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        arguments?.clear()
    }
}