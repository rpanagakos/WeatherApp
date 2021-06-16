package com.example.weatherapp.ui.fragments

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.abstraction.AbstractFragment
import com.example.weatherapp.abstraction.LocalModel
import com.example.weatherapp.abstraction.Utils.hideKeyboard
import com.example.weatherapp.abstraction.Utils.setSafeOnClickListener
import com.example.weatherapp.abstraction.Utils.showKeyboard
import com.example.weatherapp.database.LocationsEntity
import com.example.weatherapp.databinding.FragmentLocationsBinding
import com.example.weatherapp.ui.recyclerview.SwipeToDelete
import com.example.weatherapp.ui.recyclerview.WeatherAdapter
import com.example.weatherapp.ui.recyclerview.holders.LocationViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.holder_location_item.view.*


@AndroidEntryPoint
class LocationsFragment : AbstractFragment() {

    lateinit var binding: FragmentLocationsBinding
    private val adapter: WeatherAdapter = WeatherAdapter(){
        (it as LocationsEntity).apply {
            binding.searchEditText.setText(this.location)
            navigateToLanding()
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

    override fun initLayout() {
        binding.locationsRecycler.adapter = adapter
        swipeToDelete(binding.locationsRecycler)
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
                    binding.noLocations = false
                    adapter.submitList(it as List<LocalModel>?)
                }
                else -> {
                    binding.noLocations = true
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

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete =
                    adapter.currentList[viewHolder.absoluteAdapterPosition] as LocationsEntity
                viewModel.deleteLocation(itemToDelete)
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                val foregroundView: View = (viewHolder as LocationViewHolder).itemView.locationConstraint
                getDefaultUIUtil().clearView(foregroundView)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val foregroundView: View = (viewHolder as LocationViewHolder).itemView.locationConstraint
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)

            }

            override fun onChildDrawOver(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder?,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val foregroundView: View = (viewHolder as LocationViewHolder).itemView.locationConstraint
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}