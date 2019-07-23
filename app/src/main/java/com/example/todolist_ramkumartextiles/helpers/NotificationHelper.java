package com.example.todolist_ramkumartextiles.helpers;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.todolist_ramkumartextiles.R;
import com.example.todolist_ramkumartextiles.fragment.AssignTaskFrag;

public class NotificationHelper {


    private final static String CHANNEL_ID = "notification_id";

    public static void displayNotification(Context context,String title, String body){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID )
                .setSmallIcon(R.drawable.ic_home_black_24dp).setContentTitle(title).setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mBuilder.build());


    }
}
