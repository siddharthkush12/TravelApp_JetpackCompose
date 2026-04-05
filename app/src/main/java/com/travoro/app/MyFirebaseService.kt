package com.travoro.app

import android.util.Log
import com.travoro.app.ui.utils.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        val title = message.data["title"] ?: "Travel Suggestion"
        val body = message.data["body"] ?: "Explore new places"

        NotificationHelper.showNotification(
            applicationContext,
            title,
            body
        )
    }
}