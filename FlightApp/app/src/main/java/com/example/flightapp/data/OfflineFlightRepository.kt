package com.example.flightapp.data

import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(private val flightDao: FlightDao) : FlightRepository{
    override fun getAllFlightsStream(): Flow<List<Flights>> = flightDao.getAllFlights()

    override fun getFlightsByQuery(searchQuery: String): Flow<List<Flights>> = flightDao.getFlightByQuery(searchQuery)

    override fun getFavoriteFlights(): Flow<List<Favorites>> = flightDao.getFavoriteFlights()

    override suspend fun insertFlights(favorites: Favorites) = flightDao.insert(favorites)

    override suspend fun deleteFlights(favorites: Favorites) = flightDao.delete(favorites)
//
//    override suspend fun updateFlights(Flights: Flights) = flightDao.update(Flights)
}