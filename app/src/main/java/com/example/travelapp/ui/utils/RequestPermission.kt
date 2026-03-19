package com.example.travelapp.ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@Composable
fun RequestLocationPermission(
    onLocPermissionGranted: () -> Unit,
    onLocPermissionDenied: () -> Unit
){
    val context= LocalContext.current
    val sharedPref=context.getSharedPreferences("app_prefs",Context.MODE_PRIVATE)
    val alreadyAsked=sharedPref.getBoolean("location_permission_already_asked",false)


    val permissionLauncher=rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){isGranted->
        sharedPref.edit().putBoolean("location_permission_already_asked",true).apply()
        if(isGranted){
            onLocPermissionGranted()
        }
        else{
            onLocPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        val permissionGranted= ContextCompat.checkSelfPermission(
            context,Manifest.permission.ACCESS_FINE_LOCATION
        )==android.content.pm.PackageManager.PERMISSION_GRANTED

        when{
            permissionGranted->{
                onLocPermissionGranted()
            }
            !alreadyAsked->{
                permissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            else->{
                onLocPermissionDenied()
            }
        }
    }
}


@Composable
fun RequestNotificationPermission(
    onNotificationPermissionGranted: () -> Unit,
    onNotificationPermissionDenied: () -> Unit
) {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onNotificationPermissionGranted()
        else onNotificationPermissionDenied()
    }

    LaunchedEffect(Unit) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            onNotificationPermissionGranted()
            return@LaunchedEffect
        }

        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) {
            onNotificationPermissionGranted()
        } else {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}