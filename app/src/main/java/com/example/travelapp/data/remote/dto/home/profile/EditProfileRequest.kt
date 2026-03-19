package com.example.travelapp.data.remote.dto.home.profile



data class EditProfileRequest(
    val fullname:String,
    val profilePic:String,
    val dob: String,
    val phone: String,
    val gender: String,
    val city: String,
    val state: String,
    val country: String
)