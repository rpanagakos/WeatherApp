package com.example.weatherapp.abstraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class AbstractFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutRes(), container, false)

    abstract fun getLayoutRes(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(view)
    }

    abstract fun initLayout(view: View)

    override fun onPause() {
        super.onPause()
        stopOperations()
    }

    abstract fun stopOperations()
}