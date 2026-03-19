package com.example.travelapp.ui.home.TravelAI


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.travelapp.data.remote.dto.travelAi.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.flow.collectLatest
import com.example.travelapp.R
import com.example.travelapp.ui.home.navigation.AddMembersTab



@Composable
fun TravelAITabScreen(
    viewModel: TravelAiViewModel = hiltViewModel(),
    navController: NavController
) {

    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.chatbot)
    )

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }



    /* -------- Navigation Events -------- */

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest { event ->
            when(event){
                is TravelAiViewModel.TravelAiNavigation.NavigateToAddMembers->{
                    navController.navigate(AddMembersTab(event.tripId))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            TealCyan,
                            TealCyan.copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(start = 16.dp).padding(top=10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "AI Trip Planner",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            LottieAnimation(
                composition = composition,
                iterations = Int.MAX_VALUE,
                modifier = Modifier.height(100.dp)
            )
        }


        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(
                    message = message,
                    onOptionClick = { viewModel.onOptionSelected(it) },
                    onAccept = { viewModel.acceptTrip(it) }
                )

            }
        }
    }
}


@Composable
fun ChatMessageItem(
    message: AiChatMessage,
    onOptionClick: (String) -> Unit,
    onAccept: (AiTripData) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        when {
            message.tripResult != null -> {
                TripResultCard(
                    trip = message.tripResult,
                    onAccept = onAccept,
                    onRegenerate = { onOptionClick("Regenerate Trip") }
                )
            }

            message.isUser -> {
                UserBubble(message.text ?: "")
            }

            else -> {
                AiBubble(message.text ?: "")
            }
        }


        message.options?.let { options ->
            Spacer(modifier = Modifier.height(5.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEach { option ->
                    SuggestionChip(
                        onClick = { onOptionClick(option) },
                        label = { Text(option) },
                        shape = RoundedCornerShape(12.dp),
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color.White,
                            labelColor = TealCyan
                        ),
                        border = BorderStroke(
                            2.dp,
                            TealCyan.copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun AiBubble(text: String) {


    Surface(
        color = Color.White,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 20.dp,
            bottomStart = 20.dp,
            bottomEnd = 20.dp
        ),
        shadowElevation = 12.dp,
        modifier = Modifier.widthIn(max = 300.dp)
    ) {

        Text(
            text = text,
            modifier = Modifier.padding(14.dp),
            fontSize = 13.sp
        )

    }
}


@Composable
fun UserBubble(text: String) {
    Surface(
        color = TealCyan,
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 0.dp,
            bottomStart = 20.dp,
            bottomEnd = 20.dp
        ),
        modifier = Modifier.widthIn(max = 300.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(14.dp),
            fontSize = 13.sp
        )
    }
}


@Composable
fun TripResultCard(
    trip: AiTripData,
    onAccept: (AiTripData) -> Unit,
    onRegenerate: () -> Unit
) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {

        Column {


            Box(modifier = Modifier.height(240.dp)) {

                AsyncImage(
                    model = trip.images.firstOrNull(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {

                    Text(
                        trip.title,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            trip.destination,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {


                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    InfoBadge(
                        "💰 ₹${trip.budget}",
                        Color(0xFFE8F5E9),
                        Color(0xFF2E7D32)
                    )

                    InfoBadge(
                        "🗓 ${trip.bestTimeToVisit}",
                        Color(0xFFFFF3E0),
                        Color(0xFFEF6C00)
                    )

                    InfoBadge(
                        "🚗 ${trip.recommendedTransport}",
                        Color(0xFFE3F2FD),
                        Color(0xFF1565C0)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))


                SectionHeader("🌍 Why this destination?")
                Text(
                    trip.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))


                SectionHeader("📸 Photos")

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(trip.images) { image ->
                        AsyncImage(
                            model = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(14.dp))
                        )

                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                SectionHeader("📅 Daily Itinerary")

                trip.itinerary.forEach { day ->
                    ItineraryItem(day)
                }

                Spacer(modifier = Modifier.height(24.dp))


                SectionHeader("🏨 Recommended Hotels")

                trip.hotels.forEach {

                    Column(modifier = Modifier.padding(vertical = 6.dp)) {

                        Text(it.name, fontWeight = FontWeight.SemiBold)

                        Text(it.priceRange, color = Color.Gray)

                        Text(
                            it.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // RESTAURANTS
                SectionHeader("🍽 Restaurants")

                trip.restaurants.forEach {

                    Column(modifier = Modifier.padding(vertical = 6.dp)) {

                        Text(it.name, fontWeight = FontWeight.SemiBold)

                        Text(it.specialty, color = Color.Gray)

                        Text(
                            it.location,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // BUDGET BREAKDOWN
                SectionHeader("💰 Budget Breakdown")

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                    Text("Transport: ${trip.budgetBreakdown.transport}")
                    Text("Accommodation: ${trip.budgetBreakdown.accommodation}")
                    Text("Food: ${trip.budgetBreakdown.food}")
                    Text("Activities: ${trip.budgetBreakdown.activities}")

                }

                Spacer(modifier = Modifier.height(24.dp))

                // TRAVEL TIPS
                SectionHeader("💡 Travel Tips")

                trip.travelTips.forEach {
                    Text("• $it")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // BUTTONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = { onAccept(trip) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TealCyan
                        )
                    ) {
                        Text("Accept Trip")
                    }

                    OutlinedButton(
                        onClick = onRegenerate,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Regenerate")
                    }
                }
            }
        }
    }
}

@Composable
fun ItineraryItem(day: Itinerary) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp)
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        TealCyan.copy(alpha = 0.1f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    day.day.toString(),
                    color = TealCyan,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .background(
                        TealCyan.copy(alpha = 0.2f)
                    )
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            day.plan,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}


@Composable
fun InfoBadge(
    text: String,
    containerColor: Color,
    contentColor: Color
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )
        )
    }
}