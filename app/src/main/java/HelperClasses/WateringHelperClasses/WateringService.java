package HelperClasses.WateringHelperClasses;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pracadyplomowa.R;

import DataBase.SharedPreferencesNames;
import HelperClasses.ToolClass;
import MainClass.MainActivity;

public class WateringService extends Service {

    private CountDownTimer countDownTimer;

    public static final String NAME_OF_BROADCAST_INTENT = "nameOfBroadcastIntent";
    Intent broadcastIntent = new Intent(NAME_OF_BROADCAST_INTENT);
    int time;

    private NotificationCompat.Builder notificationn;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        time = intent.getIntExtra(SharedPreferencesNames.WateringData.TIME,0)*60000;
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.image_control_of_water);

        Notification notification = new NotificationCompat.Builder(this, WateringChannel.ID_OF_CHANNEL_1)
                .setContentTitle("Usługa nawadniania aktywna")
                .setSmallIcon(R.drawable.icon_drop_of_water)
                .setLargeIcon(icon)
                .build();

        startForeground(1, notification);

        countDownTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                broadcastIntent.putExtra(SharedPreferencesNames.WateringData.TIME, millisUntilFinished/1000);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesNames.WateringData.TIMER_ENABLED, true);
                editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
                editor.apply();
                sendBroadcast(broadcastIntent);
                notificationn = new NotificationCompat.Builder(getApplicationContext(),WateringChannel.ID_OF_CHANNEL_2)
                        .setSmallIcon(R.drawable.icon_drop_of_water)
                        .setContentTitle("Tura " + sharedPreferences.getInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND,1))
                        .setContentText("Pozostały czas: " + ToolClass.generateCountDownTimerTime(millisUntilFinished/1000))
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setOngoing(true)
                        .setOnlyAlertOnce(true);

                notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(2, notificationn.build());
            }

            @Override
            public void onFinish() {
                broadcastIntent.putExtra(SharedPreferencesNames.WateringData.TIMER_ENABLED, false);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesNames.WateringData.TIMER_ENABLED, false);
                editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,true);
                editor.apply();
                sendBroadcast(broadcastIntent);
                notificationManager.cancelAll();

                Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                        0, activityIntent, PendingIntent.FLAG_IMMUTABLE);


                notificationn.setContentText("Kliknij aby kontynuować!")
                        .setContentTitle("Tura zakończona")
                        .setOngoing(false)
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setOnlyAlertOnce(true);
                notificationManager.notify(2, notificationn.build());
            }
        }.start();


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SharedPreferencesNames.WateringData.TIMER_ENABLED, false);
        editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,true);
        editor.apply();
        countDownTimer.cancel();
        notificationManager.cancelAll();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
