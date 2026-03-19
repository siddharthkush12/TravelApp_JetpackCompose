package com.example.travelapp.ui.home.navigationDrawer.Notification

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.travelapp.data.remote.dto.Suggestion.Data
import com.example.travelapp.di.Session
import com.example.travelapp.ui.components.CustomTopBar
import com.example.travelapp.ui.theme.TealCyan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel= hiltViewModel(),
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val suggestions by viewModel.suggestions.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.fetchNotification()
    }


    Column(
        modifier= Modifier.fillMaxSize()
    ) {

        CustomTopBar(
            title = "Notification",
            onBackClick = { navController.popBackStack() }
        )
        when (state) {

            is NotificationViewModel.NotificationState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = TealCyan)
                }
            }

            is NotificationViewModel.NotificationState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Something went wrong")
                }
            }

            is NotificationViewModel.NotificationState.Success -> {

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    item {
                        Text(
                            text = "Recent",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }

                    items(suggestions) { item ->
                        NotificationItem(item)
                    }
                }
            }

            else -> {}
        }


    }
}




@Composable
fun NotificationItem(item: Data) {

    val icons = listOf(
        Icons.Default.TravelExplore,
        Icons.Default.Flight,
        Icons.Default.Landscape,
        Icons.Default.Place
    )

    val randomIcon = remember { icons.random() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {

        // ICON BOX
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFE6F7F7)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = randomIcon,
                contentDescription = null,
                tint = TealCyan
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = item.title ?: "",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.description ?: "",
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatTimeAgo(item.createdAt),
                fontSize = 11.sp,
                color = Color.LightGray
            )
        }
    }
}


fun formatTimeAgo(time: String?): String {

    if (time == null) return ""

    return try {

        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(time)
        val now = Date()

        val diff = now.time - date!!.time

        val minutes = diff / (1000 * 60)
        val hours = minutes / 60

        when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "$minutes min ago"
            hours < 24 -> "$hours hr ago"
            else -> "${hours / 24} days ago"
        }

    } catch (e: Exception) {
        ""
    }
}