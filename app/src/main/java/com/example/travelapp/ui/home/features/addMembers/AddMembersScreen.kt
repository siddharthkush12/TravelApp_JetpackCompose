package com.example.travelapp.ui.home.features.addMembers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.rounded.GroupAdd
import androidx.compose.material.icons.rounded.LinkOff
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travelapp.data.remote.dto.trips.Profile
import com.example.travelapp.ui.components.CustomTopBar
import com.example.travelapp.ui.home.homeNavigation.ChatGroupTab
import com.example.travelapp.ui.theme.ErrorRed
import com.example.travelapp.ui.theme.TealCyan
import com.example.travelapp.ui.theme.TealCyanLight

@Composable
fun AddMembersScreen(
    tripId: String,
    onNavigateBack: () -> Unit,
    viewModel: AddMembersViewModel = hiltViewModel(),
    navController: NavController
) {
    val members by viewModel.members.collectAsStateWithLifecycle()
    val phone by viewModel.memberPhone.collectAsStateWithLifecycle()
    val dynamicColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f))) {

        CustomTopBar(
            title = "SQUAD INVITATION", icon = Icons.Rounded.GroupAdd, onBackClick = onNavigateBack
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    text = "AUTHORIZE EXTERNAL NODES", style = TextStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        color = TealCyan
                    ), modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )
            }


            item {
                MemberInputCard(
                    value = phone,
                    onValueChange = { viewModel.onPhoneChange(it) },
                    onSearchClick = { viewModel.searchMember() })
            }


            if (members.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "VERIFIED CREW (${members.size})", style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = dynamicColor.copy(alpha = 0.5f)
                        ), modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            items(members) { member ->
                AuthorizedNodeItem(
                    member = member, onRemoveClick = { viewModel.removeMember(member) })
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        viewModel.addMembersToTrip(tripId)
                        navController.navigate(ChatGroupTab)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, TealCyan.copy(alpha = 0.4f), RoundedCornerShape(18.dp)),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TealCyan
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "INITIALIZE SECURE COMMS", style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun MemberInputCard(
    value: String, onValueChange: (String) -> Unit, onSearchClick: () -> Unit
) {
    val dynamicColor = MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "Enter node identifier (phone)...",
                        fontSize = 13.sp,
                        color = dynamicColor.copy(alpha = 0.4f)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = dynamicColor,
                    unfocusedTextColor = dynamicColor.copy(alpha = 0.9f),
                    focusedContainerColor = dynamicColor.copy(alpha = 0.05f),
                    unfocusedContainerColor = dynamicColor.copy(alpha = 0.02f),
                    focusedBorderColor = TealCyan,
                    unfocusedBorderColor = dynamicColor.copy(alpha = 0.15f),
                    cursorColor = TealCyan
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(TealCyan)
                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AuthorizedNodeItem(
    member: Profile, onRemoveClick: () -> Unit
) {
    val dynamicColor = MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier.border(
                        2.dp,
                        Brush.sweepGradient(listOf(TealCyan, TealCyanLight, TealCyan)),
                        CircleShape
                    )
                ) {
                    AsyncImage(
                        model = member.profilePic,
                        contentDescription = null,
                        modifier = Modifier
                            .size(46.dp)
                            .padding(3.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = member.fullname.uppercase(),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp,
                            color = dynamicColor.copy(alpha = 0.9f)
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = member.phone, style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp,
                            color = dynamicColor.copy(alpha = 0.5f)
                        )
                    )
                }
            }


            OutlinedButton(
                onClick = onRemoveClick,
                modifier = Modifier.height(34.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, ErrorRed.copy(alpha = 0.3f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ErrorRed, containerColor = ErrorRed.copy(alpha = 0.05f)
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.LinkOff,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "UNLINK", style = TextStyle(
                        fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                    )
                )
            }
        }
    }
}