package HelperClasses;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.pracadyplomowa.R;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String ID_OF_CHANNEL= "id";
    public static final String NAME_OF_CHANNEL = "name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(ID_OF_CHANNEL, NAME_OF_CHANNEL, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Bitmap icon=BitmapFactory.decodeResource(getResources(), R.drawable.image_skull);
        String title="Zabieg pielęgnacyjny";
        Calendar c= Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String stringMinute;
        if(minute<10)
            stringMinute="0"+minute;
        else
            stringMinute=String.valueOf(minute);
        return new NotificationCompat.Builder(getApplicationContext(), ID_OF_CHANNEL)
                .setSmallIcon(R.drawable.image_attention)
                .setContentTitle(title)
                .setContentText("O godzinie " + ++hour + ":" + stringMinute + " zaplanowałeś zabieg pielęgnacyjny. Czas najwyższy rozpocząć przygotowania!")
                .setLargeIcon(icon)
                .setAutoCancel(true);
    }
}
