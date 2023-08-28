package com.example.flightapp.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightapp.data.Favorites
import com.example.flightapp.data.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesVIewModel(
private val flightRepository: FlightRepository,  ) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _uiStateSearch = MutableStateFlow(FavoriteUiState())
    val uiStateSearch = _uiStateSearch.asStateFlow()


    val favoriteUiState: StateFlow<FavoriteUiState> =
        flightRepository.getFavoriteFlights().map { FavoriteUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoriteUiState()
            )


    fun updateSearchStarted(favorite: Boolean){
             _uiStateSearch.update { currentState ->
                 currentState.copy(
                    favorite = favorite
                 ) }
      }

    fun deleteFavorite(favorites: Favorites){
        viewModelScope.launch {
            flightRepository.deleteFlights(favorites)
        }

    }

}

/**
 * Ui State for HomeScreen
 */
data class FavoriteUiState(
    val itemList: List<Favorites> = listOf(),
    val favorite:Boolean= false
    )

