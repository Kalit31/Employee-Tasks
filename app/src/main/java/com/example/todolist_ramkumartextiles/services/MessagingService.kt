package com.example.todolist_ramkumartextiles.services

import com.example.todolist_ramkumartextiles.helpers.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage!!.notification != null) {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            NotificationHelper.displayNotification(applicationContext, title!!, body!!)
        }
    }
}
