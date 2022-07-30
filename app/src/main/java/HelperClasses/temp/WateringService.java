package HelperClasses.temp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.pracadyplomowa.R;

import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.ControlOfWater.WaterPepperFragment;
import MainClass.MainActivity;

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
        System.out.println("CZAS SERWISOWY:"+ time);



        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, WateringChannel.ID_OF_CHANNEL)
                .setContentTitle("Watering Service")
                .setContentText("I' m watering pepper now!")
                .setSmallIcon(R.drawable.icon_information)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println(millisUntilFinished/1000);
                broadcastIntent.putExtra(SharedPreferencesNames.WateringData.TIME, millisUntilFinished/1000);
                broadcastIntent.putExtra(SharedPreferencesNames.WateringData.TIMER_ENABLED, true);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED, true);
                editor.apply();
                sendBroadcast(broadcastIntent);
            }

            @Override
            public void onFinish() {
                broadcastIntent.putExtra(SharedPreferencesNames.WateringData.TIMER_ENABLED, false);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED, false);
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
        editor.putBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED, false);
        editor.putInt(SharedPreferencesNames.WateringData.TIME,time);
        editor.apply();
        System.out.println("STOP " + sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED,false));
        countDownTimer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
