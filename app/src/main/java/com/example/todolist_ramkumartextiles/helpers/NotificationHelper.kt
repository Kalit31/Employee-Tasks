package com.example.todolist_ramkumartextiles.helpers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todolist_ramkumartextiles.R
import com.example.todolist_ramkumartextiles.tasks.views.activity.EmployeeActivity

object NotificationHelper {
    private val CHANNEL_ID = "notification_id"

    fun displayNotification(context: Context, title: String, body: String) {

        val intent = Intent(context, EmployeeActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT)


        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_assignment_black_24dp)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_SOUND) as NotificationCompat.Builder
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, mBuilder.build())
    }
}
