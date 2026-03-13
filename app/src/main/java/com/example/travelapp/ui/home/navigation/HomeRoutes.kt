package com.example.travelapp.ui.home.navigation

import kotlinx.serialization.Serializable


@Serializable
object HomeTab

@Serializable
object TravelAITab

@Serializable
object SearchTab

@Serializable
data class MessageTab(
    val receiverId: String
)

@Serializable
object MyTripsTab


@Serializable
object MyProfileTab


@Serializable
object MyAccount

@Serializable
object ChatListTab


@Serializable
object CreateTripTab


