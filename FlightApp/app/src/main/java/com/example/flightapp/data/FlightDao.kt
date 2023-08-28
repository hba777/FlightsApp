package com.example.flightapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorites: Favorites)

    @Delete
    suspend fun delete(favorites: Favorites)


    @Query("SELECT * from airport WHERE name LIKE :searchQuery" )
    fun getFlightByQuery(searchQuery: String): Flow<List<Flights>>


    @Query("SELECT * from airport ORDER BY name ASC")
    fun getAllFlights(): Flow<List<Flights>>

    @Query("SELECT * from favorite")
    fun getFavoriteFlights(): Flow<List<Favorites>>
}