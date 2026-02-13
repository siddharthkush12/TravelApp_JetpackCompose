package com.example.travelapp.ui.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
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

    object Expense: HomeBottomNavigation(
        destination= ExpenseTab,
        title="Expense",
        icon= R.drawable.shopping_bag
    )

    object Messages: HomeBottomNavigation(
        destination= MessageTab,
        title="Message",
        icon= R.drawable.chat
    )

    object Profile: HomeBottomNavigation(
        destination= ProfileTab,
        title="Profile",
        icon= R.drawable.profile
    )


}
