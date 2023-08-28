package com.example.flightapp.data

import kotlinx.coroutines.flow.Flow

interface FlightRepository {

    /**
     * Retrieve all the Flightss from the the given data source.
     */
    fun getAllFlightsStream(): Flow<List<Flights>>


    fun getFlightsByQuery(searchQuery:String): Flow<List<Flights>>

    /** Function to get list of flights in favorite table
     * **/
    fun getFavoriteFlights(): Flow<List<Favorites>>

    /**
     * Insert item in the data source
     */
    suspend fun insertFlights(favorites: Favorites)

    /**
     * Delete item from the data source
     */
    suspend fun deleteFlights(favorites: Favorites)

}