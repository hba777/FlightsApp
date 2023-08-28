package com.example.flightapp.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightapp.data.Favorites
import com.example.flightapp.data.FlightRepository
import com.example.flightapp.data.Flights
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val flightRepository: FlightRepository,  ) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _uiStateSearch = MutableStateFlow(HomeUiState())
    val uiStateSearch = _uiStateSearch.asStateFlow()

    /** Function to display all lists at the app run
    val homeUiState: StateFlow<HomeUiState> =
        flightRepository.getAllFlightsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
**/
    fun getFlightsBySearchQuery(searchQuery: String): Flow<List<Flights>> {
        return flightRepository.getFlightsByQuery("%$searchQuery%")
    }

    fun saveFavorite(sourceId: Flights){
        viewModelScope.launch {

            val target = Favorites(sourceId.id,sourceId.iataCode,"HEH")
            flightRepository.insertFlights(target)

        }
    }

    fun updateQuery(query: String){
        _uiStateSearch.update { currentState ->
            currentState.copy(
                query = query
            )
        }
    }

    /**
     * Used if searchkey used
     *  fun updateSearchStarted(searchStarted: Boolean){
     *         _uiStateSearch.update { currentState ->
     *             currentState.copy(
     *                 searchStarted = searchStarted
     *             )
     *         }
     *     }
     */

}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(
    val itemList: List<Flights> = listOf(),
    val query: String= "",
   /** Used if search key used
     * val searchStarted: Boolean = false
**/
)

data class FlightUiState(
    val flights: FlightDetails = FlightDetails()
)

data class FlightDetails(
    val id: Int = 0,
    val iataCode: String = "",
    val name: String = "",
    val passengers: String = "",
)

//data class FlightsExtra(
//    val id:Int =0,
//    val departureCode:String="",
//    val destinationCode: String=""
//)

fun FlightDetails.toFlights(): Flights = Flights(
    id = id,
    name = name,
    iataCode = iataCode,
    passengers = passengers.toIntOrNull() ?: 0
)

