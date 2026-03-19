package com.example.travelapp.ui.home.navigation

import com.example.travelapp.R

sealed class HomeBottomNavigation(
    val destination: Any,
    val title: String,
    val icon: Int
){
    object Home: HomeBottomNavigation(
        destination= HomeTab,
        title="Home",
        icon= R.drawable.home
    )

    object TravelAI: HomeBottomNavigation(
        destination= TravelAITab,
        title="TravelAi",
        icon= R.drawable.ai_svgrepo_com
    )

    object Messages: HomeBottomNavigation(
        destination= ChatGroupTab,
        title="Message",
        icon= R.drawable.chat
    )

    object MyTrips: HomeBottomNavigation(
        destination= MyTripsTab,
        title="My Trips",
        icon= R.drawable.profile
    )


}
