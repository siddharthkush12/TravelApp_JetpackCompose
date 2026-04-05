package com.travoro.app.data.remote.dto.travelAi

data class AiChatMessage(
    val text: String?=null,
    val isUser: Boolean=false,
    val options:List<String>?=null,
    val tripResult: AiTripData?=null
)


data class DeleteTripResponse(
    val success: Boolean,
    val message: String
)
