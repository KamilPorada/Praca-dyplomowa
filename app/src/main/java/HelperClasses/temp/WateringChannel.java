package HelperClasses.temp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class WateringChannel extends Application {
    public static final String ID_OF_CHANNEL = "wateringChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel wateringChannel = new NotificationChannel(
                    ID_OF_CHANNEL,
                    "Watering channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(wateringChannel);
        }
    }
}
