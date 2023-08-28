package com.example.flightapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightapp.ui.theme.screens.FavoritesVIewModel
import com.example.flightapp.ui.theme.screens.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
              //  this.createSavedStateHandle(),
                inventoryApplication().container.flightRepository)

        }
        initializer {
            FavoritesVIewModel(
                inventoryApplication().container.flightRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): FlightApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)