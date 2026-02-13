package com.example.travelapp.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travelapp.ui.home.Search.SearchTabScreen
import com.example.travelapp.ui.home.dashboard.HomeTabScreen
import com.example.travelapp.ui.home.expense.ExpenseTabScreen
import com.example.travelapp.ui.home.message.MessageTabScreen
import com.example.travelapp.ui.home.profile.ProfileTabScreen


@Composable
fun HomeNavGraph(
    navController: NavHostController
){
    NavHost(
        navController=navController,
        startDestination = HomeTab
    ){
        composable<HomeTab>{
            HomeTabScreen()
        }
        composable<ExpenseTab>{
            ExpenseTabScreen()
        }
        composable<MessageTab>{
            MessageTabScreen()
        }
        composable<ProfileTab>{
            ProfileTabScreen()
        }
        composable<SearchTab> {
            SearchTabScreen()
        }
    }
}