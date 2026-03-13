package com.example.travelapp.ui.home.dashboard


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.travelapp.ui.home.profile.ProfileViewModel


@Composable
fun HomeTabScreen(

    homeViewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    homeNavController: NavController
) {

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val weather by homeViewModel.weather.collectAsStateWithLifecycle()
    val city by homeViewModel.city.collectAsStateWithLifecycle()
    val profile by profileViewModel.profile.collectAsStateWithLifecycle()




    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp)
    ) {
        item {

            HomeHeader(
                userName = profile?.fullname ?: "Hello",
                upcomingTripDate = "2026-12-01"
            )

            HomeBanner()

            when (uiState) {
                is HomeViewModel.HomeEvent.Loading -> {
                    WeatherCardPlaceholder()
                }

                is HomeViewModel.HomeEvent.Error -> {
                    Text(text = (uiState as HomeViewModel.HomeEvent.Error).message)
                }

                else -> {
                    HomeWeather(
                        city = city,
                        weather = weather
                    )
                }
            }

            HomeOptionCards(homeNavController)
        }

    }
}

