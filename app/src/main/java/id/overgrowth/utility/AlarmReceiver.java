package id.overgrowth.utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import id.overgrowth.MainActivity;
import id.overgrowth.R;

/**
 * Created by bayu_ on 7/27/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private int MID = 100;
    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context);
        mNotifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mNotifyBuilder.setContentTitle("Siram Tanaman");
        mNotifyBuilder.setContentText("Saatnya siram tanaman").setSound(alarmSound);
        mNotifyBuilder.setAutoCancel(true).setWhen(when);
        mNotifyBuilder.setContentIntent(pendingIntent);
        mNotifyBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;
    }
}
