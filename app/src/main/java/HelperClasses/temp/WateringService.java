package HelperClasses.temp;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.pracadyplomowa.R;

import DataBase.SharedPreferencesNames;

public class WateringService extends Service {

    private CountDownTimer countDownTimer;

    public static final String NAME_OF_BROADCAST_INTENT = "nameOfBroadcastIntent";
    Intent broadcastIntent = new Intent(NAME_OF_BROADCAST_INTENT);
    int time;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        time = intent.getIntExtra(SharedPreferencesNames.WateringData.TIME,0)*60000;

        Notification notification = new NotificationCompat.Builder(this, WateringChannel.ID_OF_CHANNEL_1)
                .setContentTitle("Usługa podlewania")
                .setContentText("Trwa nawadnianie plantacji papryki!")
                .setSmallIcon(R.drawable.image_tap)
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
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
