package com.naruto.voyagebus

import java.util.Locale
import kotlin.math.abs
import kotlin.random.Random

object BusData {

    val cities = listOf(
        "Delhi", "Kanpur", "Lucknow", "Ghaziabad", "Agra", "Meerut",
        "Varanasi", "Prayagraj", "Bareilly", "Aligarh", "Moradabad",
        "Saharanpur", "Gorakhpur", "Noida", "Firozabad", "Jhansi",
        "Muzaffarnagar", "Mathura", "Ayodhya", "Rampur", "Shahjahanpur",
        "Farrukhabad", "Hapur", "Etawah", "Mirzapur", "Bulandshahr",
        "Sambhal", "Amroha", "Hardoi", "Fatehpur", "Rae Bareli",
    )

    val busNames = listOf(
        "Voyage Express", "Royal Travels", "City Rider", "Super Deluxe",
        "Highway King", "Golden Route", "Metro Express", "Comfort Line",
        "Fast Track", "Premium Coach", "Smart Ride", "Blue Star",
        "Green Line", "Awadh Express", "Shatabdi Travels", "Capital Rider",
        "National Express", "Elite Bus", "Road Master",
    )

    val busList: List<Bus> by lazy {
        val buses = mutableListOf<Bus>()

        for (fromIndex in cities.indices) {
            for (toIndex in cities.indices) {
                if (fromIndex == toIndex) continue

                val from = cities[fromIndex]
                val to = cities[toIndex]

                for (i in 0 until 19) {
                    // Random time generation
                    val hour = Random.nextInt(5, 23)
                    val minute = Random.nextInt(0, 4) * 15 // 00, 15, 30, 45
                    val amPm = if (hour < 12) "AM" else "PM"
                    val displayHour = when {
                        hour == 0 -> 12
                        hour > 12 -> hour - 12
                        else -> hour
                    }
                    val formattedTime = String.format(Locale.US, "%02d:%02d %s", displayHour, minute, amPm)

                    buses.add(
                        Bus(
                            busName = busNames[i % busNames.size],
                            from = from,
                            to = to,
                            departureTime = formattedTime,
                            fare = 300 + (i * 50) + (abs(fromIndex - toIndex) * 15),
                            availableSeats = Random.nextInt(5, 41),
                        )
                    )
                }
            }
        }
        buses
    }
}
