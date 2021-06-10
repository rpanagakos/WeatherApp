package com.example.weatherapp.abstraction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractActivity(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initLayout()
    }

    abstract fun initLayout()

    override fun onPostResume() {
        super.onPostResume()
        runOperation()
    }


    abstract fun runOperation()

    override fun onStop() {
        stopOperation()
        super.onStop()
    }

    abstract fun stopOperation()

    override fun onDestroy() {
        super.onDestroy()
    }
}