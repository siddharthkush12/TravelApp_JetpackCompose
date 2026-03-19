package com.example.travelapp.data.remote.dto.home.profile

data class FetchProfileResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)

