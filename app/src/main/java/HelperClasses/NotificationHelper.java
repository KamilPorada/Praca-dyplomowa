package HelperClasses;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.pracadyplomowa.R;

import MainClass.MainActivity;

public class NotificationHelper extends ContextWrapper {
    public static final String ID_OF_CHANNEL= "id";
    public static final String NAME_OF_CHANNEL = "name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
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
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.image_green_weed);
        return new NotificationCompat.Builder(getApplicationContext(), ID_OF_CHANNEL)
                .setSmallIcon(R.drawable.image_attention)
                .setContentTitle("Zabieg chwastobójczy")
                .setContentText("O godzinie 16:00 zaplanowałeś zabieg chwastobójczy. Czas najwyższy rozpocząć przygotowania!")
                .setLargeIcon(picture)
                .setAutoCancel(true);



//        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.image_worm);
//        Intent activityIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, activityIntent, 0);
//        return new NotificationCompat.Builder(getApplicationContext(), ID_OF_CHANNEL)
//                .setContentTitle("Zabieg pielęgnacyjny!")
//                .setContentText("Wykonaj zabieg pielęgnacyjny środkiem Roundup.")
//                .setSmallIcon(R.drawable.image_worm)
//                .setLargeIcon(picture)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(picture)
//                        .bigLargeIcon(null))
//                .setContentIntent(contentIntent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setAutoCancel(true)
//                .setOnlyAlertOnce(true);
    }
}
