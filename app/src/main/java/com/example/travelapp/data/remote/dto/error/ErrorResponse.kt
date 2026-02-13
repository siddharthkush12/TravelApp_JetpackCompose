package com.example.travelapp.data.remote.dto.error

data class ErrorResponse(
    val success: Boolean? = null,
    val code: Int? = null,
    val message: String? = null
)
