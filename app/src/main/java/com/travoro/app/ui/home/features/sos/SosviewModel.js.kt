package com.travoro.app.ui.home.features.sos

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.travoro.app.data.remote.api.TravelApiService
import com.travoro.app.data.remote.repository.TripRepository
import com.travoro.app.ui.utils.TripLocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SosViewModel @Inject constructor(

): ViewModel(){





    fun startLiveLocation(
        context: Context,
        tripId: String,
        userId: String?
    ){

        val intent = Intent(
            context,
            TripLocationService::class.java
        )

        intent.putExtra("tripId", tripId)
        intent.putExtra("userId", userId)

        ContextCompat.startForegroundService(
            context,
            intent
        )

    }


    fun stopLiveLocation(
        context: Context
    ){

        context.stopService(

            Intent(
                context,
                TripLocationService::class.java
            )

        )

    }

    fun saveLocationPreference(
        context: Context,
        enabled: Boolean
    ){
        context.getSharedPreferences(
            "trip_pref",
            Context.MODE_PRIVATE
        )
            .edit {
                putBoolean("live_location_enabled", enabled)
            }
    }



}