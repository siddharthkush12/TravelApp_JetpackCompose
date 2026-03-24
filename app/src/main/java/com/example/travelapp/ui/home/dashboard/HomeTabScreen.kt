package com.example.travelapp.ui.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travelapp.ui.home.profile.ProfileViewModel
import com.example.travelapp.ui.theme.GradientSunsetEnd
import com.example.travelapp.ui.theme.TealCyan
import com.example.travelapp.ui.theme.TealCyanDark

@Composable
fun HomeTabScreen(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel,
    homeNavController: NavController
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val weather by homeViewModel.weather.collectAsStateWithLifecycle()
    val city by homeViewModel.city.collectAsStateWithLifecycle()
    val profile by profileViewModel.profile.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {

        item {
            HomeHeader(
                userName = profile?.fullname ?: "Traveler", upcomingTripDate = "2026-12-01"
            )
            Spacer(modifier = Modifier.height(5.dp))
        }


        item {
            HomeOptionCards(homeNavController)
            Spacer(modifier = Modifier.height(24.dp))
        }


        item {
            SectionTitle(title = "Trending Destinations", subtitle = "Unfold hidden gems")
            TrendingDestinationsCarousel()
            Spacer(modifier = Modifier.height(32.dp))
        }


        item {
            AIBannerCard(onClick = { })
            Spacer(modifier = Modifier.height(32.dp))
        }


        item {
            SectionTitle(title = "Local Conditions", subtitle = city ?: "Fetching location...")

            when (uiState) {
                is HomeViewModel.HomeEvent.Loading -> {
                    WeatherCardPlaceholder()
                }

                is HomeViewModel.HomeEvent.Error -> {
                    Text(
                        text = (uiState as HomeViewModel.HomeEvent.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }

                else -> {
                    HomeWeather(
                        city = city, weather = weather
                    )
                }
            }
        }
    }
}


@Composable
fun SectionTitle(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


data class Destination(
    val name: String, val country: String, val imageUrl: String, val rating: String
)

@Composable
fun TrendingDestinationsCarousel() {
    val destinations = listOf(
        Destination(
            "Kyoto",
            "Japan",
            "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?q=80&w=800",
            "4.9"
        ), Destination(
            "Amalfi Coast",
            "Italy",
            "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?q=80&w=800",
            "4.8"
        ), Destination(
            "Banff",
            "Canada",
            "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?q=80&w=800",
            "4.9"
        )
    )

    LazyRow(

        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(destinations) { dest ->
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(280.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { /* TODO: Navigate to Details */ }) {

                AsyncImage(
                    model = dest.imageUrl,
                    contentDescription = dest.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = 300f
                            )
                        )
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = GradientSunsetEnd,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = dest.rating,
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Column {
                        Text(
                            text = dest.name,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = dest.country,
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AIBannerCard(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .background(

                Brush.linearGradient(
                    colors = listOf(
                        TealCyanDark, TealCyan
                    )
                )
            )) {

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .size(150.dp)
                .clip(RoundedCornerShape(75.dp))
                .background(Color.White.copy(alpha = 0.1f))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Travoro AI",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Build your dream itinerary in seconds.",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    lineHeight = MaterialTheme.typography.titleMedium.lineHeight
                )
            }


            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Try Now",
                    tint = Color.White
                )
            }
        }
    }
}