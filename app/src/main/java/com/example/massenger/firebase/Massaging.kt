package com.example.massenger.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import org.jetbrains.annotations.NotNull


class Massaging: FirebaseMessagingService() {

    override fun onNewToken(@NotNull token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New token: $token")

    }

    override fun onMessageReceived(@NotNull message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(TAG, "Received message: ${message.notification?.body}")

    }

}