package com.example.travelapp.data.remote.dto.auth

data class LoginResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)


