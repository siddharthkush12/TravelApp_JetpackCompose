package com.example.travelapp.ui.home.dashboard


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelapp.R
import com.example.travelapp.ui.home.navigation.CreateTripTab
import com.example.travelapp.ui.home.navigation.TravelAITab
import com.example.travelapp.ui.theme.TealCyan


@Composable
fun HomeOptionCards(
    homeNavController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                OptionItem(
                    icon = Icons.Default.Add,
                    title = "Create Trip",
                    onNavigate = { homeNavController.navigate(CreateTripTab) }
                )

                OptionItem(
                    icon = Icons.Default.AccountBalanceWallet,
                    title = "Bill Split",
                    onNavigate = { homeNavController.navigate(CreateTripTab) }
                )

                OptionItem(
                    icon = Icons.Default.Hotel,
                    title = "Hotels",
                    onNavigate = { homeNavController.navigate(CreateTripTab) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                OptionItem(
                    icon = Icons.Default.LocationOn,
                    title = "Nearby",
                    onNavigate = { homeNavController.navigate(CreateTripTab) }
                )

                OptionItem(
                    icon = Icons.Default.Map,
                    title = "Map",
                    onNavigate = { homeNavController.navigate(CreateTripTab) }
                )

                OptionItem(
                    icon = Icons.Default.Warning,
                    title = "SOS",
                    onNavigate = { homeNavController.navigate(CreateTripTab) }
                )
            }
        }
    }
}

@Composable
fun OptionItem(
    icon: ImageVector,
    title: String,
    onNavigate: ()->Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEFF7FF)),
            contentAlignment = Alignment.Center
        ) {

            IconButton(
                onClick = {onNavigate()}
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = TealCyan,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = title,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}