package com.example.weatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//This generated Hilt component is attached to the Application object's lifecycle and provides dependencies to it.
// Additionally, it is the parent component of the app, which means that other components can access the dependencies
// that it provides.
@HiltAndroidApp
class MyApplication : Application() {
}