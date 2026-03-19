package com.example.travelapp.ui.home

import android.graphics.Color.alpha
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.travelapp.R
import com.example.travelapp.di.Session
import com.example.travelapp.ui.home.dashboard.HomeViewModel
import com.example.travelapp.ui.home.navigation.HomeBottomBar
import com.example.travelapp.ui.home.navigation.HomeNavGraph
import com.example.travelapp.ui.home.navigationDrawer.NavigationDrawer
import com.example.travelapp.ui.home.profile.ProfileViewModel
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    session: Session,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val homeNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val city by homeViewModel.city.collectAsStateWithLifecycle()
    val profile by profileViewModel.profile.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        homeViewModel.fetchLocationAndCity()
    }

    LaunchedEffect(profile) {
        profile?.let {
            session.storeProfileImage(it.profilePic ?: "")
        }
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.robot)
    )


    val showTopBar = currentRoute?.contains("HomeTab") == true ||
            currentRoute?.contains("SearchTab") == true


    val showBottomBar = currentRoute?.contains("HomeTab") == true ||
            currentRoute?.contains("TravelAITab") == true ||
            currentRoute?.contains("MyTripsTab") == true ||
            currentRoute?.contains("ChatGroupTab") == true



    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {

            NavigationDrawer(
                homeNavController, navController, session, drawerState, profileViewModel
            )

        }) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    Column(
                        modifier = Modifier.background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.9f),
                                    Color.White.copy(alpha = 0.7f),
                                    Color.White.copy(alpha = 0.5f),
                                    Color.White.copy(alpha = 0.3f)
                                )
                            )
                        )
                    ) {
                        if (showTopBar) {
                            TopAppBar(
                                modifier = Modifier.height(90.dp),
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Transparent,

                                    ),

                                title = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp),
                                        horizontalArrangement = Arrangement.End

                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Location",
                                            tint = TealCyan,
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(2.dp))

                                        Text(
                                            text = city ?: "Fetching...",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }) {
                                        Icon(
                                            painter = painterResource(R.drawable.hamburger_menu_more_2_svgrepo_com),
                                            contentDescription = "menu",
                                            tint = TealCyan,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                })
                        }
                        Divider(
                            thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f)
                        )
                    }

                },

                bottomBar = {
                    if (showBottomBar) {
                        HomeBottomBar(navController = homeNavController)
                    }
                },
//                floatingActionButton = {
//                    if (currentRoute in bottomBarRoutes) {
//                        FloatingActionButton(
//                            onClick = { homeNavController.navigate(SearchTab) },
//                            modifier = Modifier.offset(y = 40.dp),
//                            containerColor = Color.Transparent,
//                            elevation = FloatingActionButtonDefaults.elevation(0.dp)
//                        ) {
//                            LottieAnimation(
//                                composition = composition,
//                                iterations = LottieConstants.IterateForever,
//                                modifier = Modifier.size(80.dp)
//                            )
//                        }
//                    }
//                },

//                floatingActionButtonPosition = FabPosition.End
            ) { paddingValues ->

                HomeNavGraph(
                    homeNavController = homeNavController,
                    profileViewModel = profileViewModel,
                    homeViewModel = homeViewModel,
                    paddingValues = paddingValues,
                    session=session
                )
            }
        }
    }
}