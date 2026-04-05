package com.travoro.app.data.remote.dto.trips





data class SearchProfileResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: Profile

)

data class Profile(
    val _id: String,
    val userId: String,
    val fullname: String,
    val phone: String,
    val profilePic: String?

)