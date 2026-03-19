 package com.example.travelapp.ui.home.navigationDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.travelapp.R
import com.example.travelapp.data.remote.dto.home.profile.Data
import com.example.travelapp.di.Session
import com.example.travelapp.di.SocketManager
import com.example.travelapp.navigation.Home
import com.example.travelapp.navigation.Login
import com.example.travelapp.ui.home.navigation.DeveloperTab
import com.example.travelapp.ui.home.navigation.MyAccount
import com.example.travelapp.ui.home.navigation.MyProfileTab
import com.example.travelapp.ui.home.navigation.Notification
import com.example.travelapp.ui.home.navigation.Support
import com.example.travelapp.ui.home.profile.ProfileViewModel
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.launch
import java.lang.System.console


 @Composable
fun NavigationDrawer(
    homeNavController: NavController,
    navController: NavController,
    session: Session,
    drawerState: DrawerState,
    viewModel: ProfileViewModel
){
    ModalDrawerSheet {

        val profile by viewModel.profile.collectAsStateWithLifecycle()

        LaunchedEffect(profile) {
            viewModel.fetchProfile()
        }




        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            AppNameTextNavigationDrawer()

            ProfileNavigationDrawer(homeNavController, drawerState,profile)

            MyAccountNavigationDrawer(homeNavController,drawerState)

            MyTripNavigationDrawer(homeNavController, drawerState)

            SettingNavigationDrawer()

            AboutDeveloperNavigationDrawer(homeNavController=homeNavController,rootNavController = navController,session,drawerState)

            AppDetailNavigationDrawer()
        }
    }
}




@Composable
fun ProfileNavigationDrawer(
    homeNavController: NavController,
    drawerState: DrawerState,
    profile: Data?
) {


    val scope= rememberCoroutineScope()
    var profilePic by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(profile) {
        profile?.let{
            profilePic=it.profilePic?:""
            fullname=it.fullname
            email=it.email
        }
    }

    val gradient = listOf(
        TealCyan.copy(alpha = 0.6f),
        TealCyan.copy(alpha = 0.3f),
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clickable{
                scope.launch{
                    homeNavController.navigate(MyProfileTab)
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
                AsyncImage(
                    model = profilePic,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hi ${fullname}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = email,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow",
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
            .padding(horizontal = 15.dp, vertical = 10.dp),
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
                text="My Trips",
                fontSize = 15.sp
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
                        text = "View/Manage Trips",
                        fontSize = 15.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
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
                        text = "Wishlist",
                        fontSize = 15.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
                )

            }
        }
    }
}



@Composable
fun SettingNavigationDrawer(){


    val scope= rememberCoroutineScope()



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
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
                text="Settings"
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
                        text = "Country",
                        fontSize = 15.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
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
                        imageVector = Icons.Default.CurrencyExchange,
                        contentDescription = "Right Arrow"
                    )
                    Text(
                        text = "Currency",
                        fontSize = 15.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
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
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Right Arrow"
                    )
                    Text(
                        text = "Communication Preference",
                        fontSize = 15.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
                )

            }
        }
    }
}





@Composable
fun MyAccountNavigationDrawer(
    homeNavController: NavController,
    drawerState: DrawerState
){
    val scope=rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
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

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Button (
                        onClick={
                            scope.launch {
                                homeNavController.navigate(MyAccount)
                                drawerState.close()
                            }
                        },
                        modifier=Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealCyan.copy(alpha = 0.5f))
                    ){
                        Icon(
                            painter=painterResource(R.drawable.account),
                            contentDescription = "account",
                            modifier=Modifier.size(25.dp),
                        )
                    }

                    Text(
                        text="My Account",
                        fontSize = 13.sp
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Button (
                        onClick={
                            scope.launch {
                                homeNavController.navigate(Support)
                                drawerState.close()
                            }
                        },
                        modifier=Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealCyan.copy(alpha = 0.5f))
                    ){
                        Icon(
                            painter=painterResource(R.drawable.support_svgrepo_com),
                            contentDescription = "support",
                            modifier=Modifier.size(25.dp),
                        )
                    }

                    Text(
                        text="Support",
                        fontSize = 13.sp
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Button (
                        onClick={
                            scope.launch {
                                homeNavController.navigate(Notification)
                                drawerState.close()
                            }
                        },
                        modifier=Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealCyan.copy(alpha = 0.5f))
                    ){
                        Icon(
                            painter=painterResource(R.drawable.notification_bing_svgrepo_com),
                            contentDescription = "support",
                            modifier=Modifier.size(25.dp),
                        )
                    }

                    Text(
                        text="Notification",
                        fontSize = 13.sp
                    )

                }
            }
        }
    }
}



@Composable
fun AboutDeveloperNavigationDrawer(
    homeNavController: NavController,
    rootNavController: NavController,
    session: Session,
    drawerState: DrawerState
){

    val scope=rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
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
                text="About Developer"
            )

            Row(
                modifier=Modifier.fillMaxWidth().clickable(
                    onClick = {
                        scope.launch {
                            homeNavController.navigate(DeveloperTab)
                            drawerState.close()
                        }
                    }
                ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = "Right Arrow"
                    )
                    Text(
                        text = "Developer",
                        fontSize = 15.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
                )


            }


            Row(
                modifier=Modifier.fillMaxWidth().clickable(
                    onClick = {
                        SocketManager.disconnect()
                        session.removeToken()
                        session.removeUserId()
                        session.removeProfileImage()
                        rootNavController.navigate(Login){
                            popUpTo(Home){
                                inclusive=true
                            }
                        }
                    }
                ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Right Arrow"
                    )
                    Text(
                        text = "Logout",
                        fontSize = 15.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Right Arrow",
                    tint = Color.Blue
                )
            }
        }
    }
}



@Composable
fun AppDetailNavigationDrawer(){
    Column(
        modifier=Modifier.padding(15.dp).fillMaxWidth().padding(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rate Us . Privacy Policy",
            color = Color.Blue,
            fontSize = 15.sp
        )

        Text(
            text="App Version 1.0.0",
            fontSize = 15.sp
        )
    }
}



@Composable
fun AppNameTextNavigationDrawer() {

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF00F5A0),
            Color(0xFF00D9F5),
            Color(0xFF7F5AF0)
        )
    )

    Text(
        text = "Travoro",
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = TextStyle(
            brush = gradient,
            fontSize = 32.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.inknutantiquamedium)),
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.25f),
                offset = Offset(2f, 2f),
                blurRadius = 8f
            )
        )
    )
}