package com.example.todolist_ramkumartextiles.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.todolist_ramkumartextiles.R;
import com.example.todolist_ramkumartextiles.activity.EmployeeActivity;
import com.example.todolist_ramkumartextiles.fragment.AssignTaskFrag;

public class NotificationHelper {


    private final static String CHANNEL_ID = "notification_id";

    public static void displayNotification(Context context,String title, String body){

        Intent intent = new Intent(context, EmployeeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity
                (context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID )
                .setSmallIcon(R.drawable.ic_assignment_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mBuilder.build());


    }
}
