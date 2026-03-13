package com.example.travelapp.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.travelapp.ui.home.Features.CreateTrips.CreateTripScreen
import com.example.travelapp.ui.home.Search.SearchTabScreen
import com.example.travelapp.ui.home.TravelAI.TravelAITabScreen
import com.example.travelapp.ui.home.dashboard.HomeTabScreen
import com.example.travelapp.ui.home.dashboard.HomeViewModel
import com.example.travelapp.ui.home.message.ChatListScreen
import com.example.travelapp.ui.home.message.MessageScreen

import com.example.travelapp.ui.home.mytrips.MyTripsScreen
import com.example.travelapp.ui.home.navigationDrawer.myaccount.MyAccountScreen
import com.example.travelapp.ui.home.profile.ProfileTabScreen
import com.example.travelapp.ui.home.profile.ProfileViewModel


@Composable
fun HomeNavGraph(
    homeNavController: NavHostController,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues

){
    NavHost(
        navController=homeNavController,
        startDestination = HomeTab
    ){
        composable<HomeTab>{
            HomeTabScreen(homeViewModel,profileViewModel,homeNavController)
        }
        composable<TravelAITab>{
            TravelAITabScreen()
        }

        composable<MyTripsTab>{
            MyTripsScreen()
        }
        composable<SearchTab> {
            SearchTabScreen()
        }
        composable<MyProfileTab> {
            ProfileTabScreen(
                onNavigateBack = {homeNavController.popBackStack()}
            )
        }
        composable<MyAccount>{
            MyAccountScreen()
        }
        composable<ChatListTab>{
            ChatListScreen(
                onChatClick = { userId ->
                    homeNavController.navigate(MessageTab(userId))
                }
            )
        }
        composable<MessageTab>{backstackEntry->
            val args=backstackEntry.toRoute<MessageTab>()
            MessageScreen(
                receiverId = args.receiverId,
                onBackClick = {
                    homeNavController.popBackStack()
                }
            )
        }

        composable<CreateTripTab> {
            CreateTripScreen(
                onNavigateBack = { homeNavController.popBackStack() }
            )
        }

    }
}