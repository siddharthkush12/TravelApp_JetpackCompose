package com.example.travelapp.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.travelapp.di.Session
import com.example.travelapp.ui.home.Features.AddMembers.AddMembersScreen
import com.example.travelapp.ui.home.Features.CreateTrips.CreateTripScreen
import com.example.travelapp.ui.home.Search.SearchTabScreen
import com.example.travelapp.ui.home.TravelAI.TravelAITabScreen
import com.example.travelapp.ui.home.dashboard.HomeTabScreen
import com.example.travelapp.ui.home.dashboard.HomeViewModel
import com.example.travelapp.ui.home.message.ChatGroupScreen
import com.example.travelapp.ui.home.message.MessageScreen
import com.example.travelapp.ui.home.mytrips.MyTripsScreen
import com.example.travelapp.ui.home.navigationDrawer.Notification.NotificationScreen
import com.example.travelapp.ui.home.navigationDrawer.Support.SupportScreen
import com.example.travelapp.ui.home.navigationDrawer.myaccount.MyAccountScreen
import com.example.travelapp.ui.home.navigationDrawer.options.DeveloperScreen
import com.example.travelapp.ui.home.profile.ProfileTabScreen
import com.example.travelapp.ui.home.profile.ProfileViewModel


@Composable
fun HomeNavGraph(
    homeNavController: NavHostController,
    profileViewModel: ProfileViewModel,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    session: Session

){
    NavHost(
        navController=homeNavController,
        startDestination = HomeTab
    ){
        composable<HomeTab>{
            HomeTabScreen(homeViewModel,profileViewModel,homeNavController)
        }
        composable<TravelAITab>{
            TravelAITabScreen(navController=homeNavController)
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
            MyAccountScreen(navController = homeNavController)
        }

        composable<Notification>{
            NotificationScreen(navController = homeNavController)
        }

        composable<Support>{
            SupportScreen(navController = homeNavController,profileViewModel=profileViewModel)
        }


        composable<DeveloperTab> {
            DeveloperScreen(navController = homeNavController)
        }


        composable< ChatGroupTab>{
            ChatGroupScreen(navController = homeNavController)
        }


        composable<CreateTripTab> {
            CreateTripScreen(
                onNavigateBack = { homeNavController.popBackStack() }
            )
        }


        composable<AddMembersTab> {backStackEntry->
            val args=backStackEntry.toRoute<AddMembersTab>()
            AddMembersScreen(
                tripId = args.tripId,
                onNavigateBack = { homeNavController.popBackStack() },
                navController = homeNavController
            )
        }

        composable<MessageScreenTab> {backStackEntry->
            val args=backStackEntry.toRoute<MessageScreenTab>()
            MessageScreen(
                groupId = args.groupId,
                userId = requireNotNull(session.getUserId()),
                onClickBack = { homeNavController.popBackStack() },
                session = session
            )
        }

    }
}