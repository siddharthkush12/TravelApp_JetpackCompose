package com.example.travelapp.data.remote.dto.auth

data class SignupRequest(
    val name:String,
    val email:String,
    val password:String
)