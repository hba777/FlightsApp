package com.example.flightapp

import android.app.Application
import com.example.flightapp.data.AppContainer
import com.example.flightapp.data.AppDataContainer

class FlightApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}