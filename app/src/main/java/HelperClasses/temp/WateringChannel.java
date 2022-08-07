package HelperClasses.temp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class WateringChannel extends Application {
    public static final String ID_OF_CHANNEL_1 = "wateringChannel1";
    public static final String ID_OF_CHANNEL_2 = "wateringChannel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel wateringChannel1 = new NotificationChannel(
                    ID_OF_CHANNEL_1,
                    "Watering channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationChannel wateringChannel2 = new NotificationChannel(
                    ID_OF_CHANNEL_2,
                    "Watering channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(wateringChannel1);
            manager.createNotificationChannel(wateringChannel2);
        }
    }
}
