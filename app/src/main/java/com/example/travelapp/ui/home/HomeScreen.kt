package com.example.travelapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.ui.components.MyTripNavigationDrawer
import com.example.travelapp.ui.components.ProfileNavigationDrawer
import com.example.travelapp.ui.home.navigation.ExpenseTab
import com.example.travelapp.ui.home.navigation.HomeBottomBar
import com.example.travelapp.ui.home.navigation.HomeNavGraph
import com.example.travelapp.ui.home.navigation.HomeTab
import com.example.travelapp.ui.home.navigation.ProfileTab
import com.example.travelapp.ui.home.navigation.SearchTab
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val homeNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        HomeTab::class.qualifiedName,
        ExpenseTab::class.qualifiedName,
        SearchTab::class.qualifiedName,
    )



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Travoro",
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                ProfileNavigationDrawer(homeNavController,drawerState)

                MyTripNavigationDrawer(homeNavController,drawerState)



                NavigationDrawerItem(
                    label = { Text(text = "Logout") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    if(currentRoute in bottomBarRoutes) {
                        HomeBottomBar(navController = homeNavController)
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { homeNavController.navigate(SearchTab) },
                        modifier = Modifier.offset(y = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.End
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {}
                HomeNavGraph(
                    navController = homeNavController,
                )
            }
        }
    }
}