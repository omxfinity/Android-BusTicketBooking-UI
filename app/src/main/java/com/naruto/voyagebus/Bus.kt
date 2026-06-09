package com.naruto.voyagebus

data class Bus(
    val busName: String,
    val from: String,
    val to: String,
    val departureTime: String,
    val fare: Int,
    val availableSeats: Int
)
