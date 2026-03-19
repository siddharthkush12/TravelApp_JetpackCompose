package com.example.travelapp.data.remote.dto.travelAi

data class AddMemberRequest(
    val memberIds: List<String>
)


data class AddMemberResponse(
    val success: Boolean,
    val message: String,
)

