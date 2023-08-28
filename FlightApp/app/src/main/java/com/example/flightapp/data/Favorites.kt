package com.example.flightapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorites(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "departure_code" )
    val departureCode: String,
    @ColumnInfo(name = "destination_code")
    val destinationCode: String,
)
