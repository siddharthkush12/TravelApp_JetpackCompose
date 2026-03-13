package com.example.travelapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.di.Session
import com.example.travelapp.navigation.NavGraph
import com.example.travelapp.ui.theme.TravelAppTheme
import com.example.travelapp.ui.utils.RequestLocationPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var travelApiService: TravelApiService

    @Inject
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var permissionGranted by remember { mutableStateOf(false) }
            var permissionChecked by remember { mutableStateOf(false) }

            TravelAppTheme {

                RequestLocationPermission(
                    onPermissionGranted = {
                        permissionChecked = true
                        permissionGranted=true
                    },
                    onPermissionDenied = {
                        permissionChecked = true
                        permissionGranted=false
                    }
                )
                when{
                    permissionChecked && permissionGranted->{
                        val navController = rememberNavController()
                        NavGraph(navController,session)
                    }
                    permissionChecked && !permissionGranted->{
                        PermissionBlockedScreen()
                    }
                }

            }
        }
    }
}


@Composable
fun PermissionBlockedScreen() {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Location Permission Required")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:${context.packageName}")
                )
                context.startActivity(intent)
            }) {
                Text("Open Settings")
            }
        }
    }
}

