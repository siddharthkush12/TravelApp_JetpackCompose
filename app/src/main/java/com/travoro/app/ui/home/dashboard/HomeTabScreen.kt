package com.travoro.app.ui.home.dashboard

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.travoro.app.data.remote.dto.others.Destination
import com.travoro.app.data.remote.dto.suggestion.Data
import com.travoro.app.di.Session
import com.travoro.app.ui.home.features.weather.TripWeatherSection
import com.travoro.app.ui.home.features.weather.TripWeatherViewModel
import com.travoro.app.ui.home.features.weather.WeatherCardPlaceholder
import com.travoro.app.ui.home.homeNavigation.TravelAITab
import com.travoro.app.ui.home.profile.ProfileViewModel
import com.travoro.app.ui.theme.GradientSunsetEnd
import com.travoro.app.ui.theme.TealCyan
import com.travoro.app.ui.theme.TealCyanDark
import com.travoro.app.ui.theme.TealCyanLight
import com.travoro.app.ui.utils.calculateDaysLeft
import com.travoro.app.ui.utils.getTimeOfDayGreeting
import kotlinx.coroutines.delay

@Composable
fun HomeTabScreen(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel,
    homeNavController: NavController,
    session: Session
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val city by homeViewModel.city.collectAsStateWithLifecycle()
    val profile by profileViewModel.profile.collectAsStateWithLifecycle()
    val trendingDestination by homeViewModel.trendingDestination.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.fetchLocationAndCity()
        delay(500)
        homeViewModel.startLocationForActiveTrips(session.getUserId())
    }

    LaunchedEffect(trendingDestination) {
        homeViewModel.fetchTrendingDestination()
    }




    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {

        item {
            HomeHeader(
                userName = profile?.fullname ?: "Traveler"
            )
            Spacer(modifier = Modifier.height(5.dp))
        }


        item {
            SectionTitle(title = "Essentials", subtitle = "Explore your travel needs")
            HomeOptionCards(homeNavController,homeViewModel)
            Spacer(modifier = Modifier.height(24.dp))
        }


        item {
            SectionTitle(title = "Trending Destinations", subtitle = "Unfold hidden gems")
            when(trendingDestination){
                is HomeViewModel.TrendingDestinationEvent.Loading->{
                    CircularProgressIndicator()
                }
                is HomeViewModel.TrendingDestinationEvent.Success->{
                    val data =
                        (trendingDestination as HomeViewModel.TrendingDestinationEvent.Success).data
                    TrendingDestinationsCarousel(data)
                }
                else->{}
            }
            Spacer(modifier = Modifier.height(32.dp))
        }


        item {
            AIBannerCard(onClick = {homeNavController.navigate(TravelAITab)})
            Spacer(modifier = Modifier.height(32.dp))
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






@Composable
fun TrendingDestinationsCarousel(
    trendingDestination: List<Data?>?
) {

    LazyRow(

        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(trendingDestination?:emptyList()) { dest ->
            if(dest!=null){
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(280.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { /* TODO: Navigate to Details */ }) {

                AsyncImage(
                    model = dest.image,
                    contentDescription = dest.title,
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
                            text = dest.rating?:"4",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Column {
                        Text(
                            text = dest.title?:"",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            }
        }
    }
}





@Composable
fun AIBannerCard(
    onClick: () -> Unit,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "ai_banner_anim")
    val sparkleRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sparkle_rot"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .background(
                Brush.linearGradient(
                    colors = listOf(TealCyanDark, TealCyan)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
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
                        modifier = Modifier
                            .size(45.dp)
                            .graphicsLayer { rotationZ = sparkleRotation }
                    )

                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = "Travoro AI",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Build your dream itinerary in seconds.",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
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
                    tint = Color.White,
                )
            }
        }
    }
}






@OptIn(ExperimentalTextApi::class)
@Composable
fun HomeHeader(
    userName: String?,
) {


    val nameGradient = remember {
        Brush.linearGradient(
            colors = listOf(
                TealCyanDark,
                TealCyanLight
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 10.dp)
    ) {


        Text(
            text = userName ?: "User",
            style = MaterialTheme.typography.displaySmall.copy(
                brush = nameGradient
            ),
            fontWeight = FontWeight.ExtraBold,
        )

        Spacer(modifier = Modifier.height((-7).dp))

        Text(
            text = "Ready to build a scalable itinerary today?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
    }
}