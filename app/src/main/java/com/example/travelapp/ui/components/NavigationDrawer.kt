package com.example.travelapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelapp.R
import com.example.travelapp.ui.home.navigation.ProfileTab
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.launch


@Composable
fun ProfileNavigationDrawer(
    homeNavController: NavController,
    drawerState: DrawerState
    ) {

    val scope= rememberCoroutineScope()

    val gradient = listOf(
        TealCyan.copy(alpha = 0.6f),
        TealCyan.copy(alpha = 0.3f),
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable{
                scope.launch{
                    homeNavController.navigate(ProfileTab)
                    drawerState.close()
                }
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),

    ) {
        Row(
            modifier = Modifier
                .background(brush = Brush.horizontalGradient(gradient))
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier.size(50.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hi Siddharth",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Siddharthkush12@gmail.com",

                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow"
            )

        }
    }
}



@Composable
fun MyTripNavigationDrawer(
    homeNavController: NavController,
    drawerState: DrawerState
) {

    val scope= rememberCoroutineScope()



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier=Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text="My Trips"
            )

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "Right Arrow"
                    )
                    Text(
                        text = "View/Manage Trips"
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow"
                )

            }

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.HeartBroken,
                        contentDescription = "Right Arrow"
                    )
                    Text(
                        text = "Wishlist"
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow"
                )

            }
        }
    }
}