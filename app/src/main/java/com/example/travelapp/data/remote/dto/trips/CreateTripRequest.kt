package com.example.travelapp.data.remote.dto.trips

data class CreateTripRequest(
    val title: String,
    val description: String?,
    val destination: String?,
    val startDate: String?,
    val endDate: String?,
    val tripType: String?,
    val travelStyle: String?,
    val budget: Int?,
    val members: List<String>
)
