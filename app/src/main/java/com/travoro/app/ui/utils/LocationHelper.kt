package com.travoro.app.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

class LocationHelper (context: Context){
    private val fusedLocationClient= LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(priority: Int): Location? =
        suspendCancellableCoroutine { continuation ->

            fusedLocationClient.getCurrentLocation(priority, null)
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }

    suspend fun getCityFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String? {
        return try{
            val geocoder= Geocoder(context,Locale.getDefault())
            val addresses=geocoder.getFromLocation(latitude,longitude,1)
            if(!addresses.isNullOrEmpty()){
                addresses[0].locality?:addresses[0].subAdminArea
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

}


