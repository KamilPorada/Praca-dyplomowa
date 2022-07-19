package MainClass;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import FragmentsClass.BottomFragments.AboutApplicationFragment;
import FragmentsClass.BottomFragments.FarmFragment;
import FragmentsClass.BottomFragments.HomeFragment;
import FragmentsClass.MainModulesFragments.Calculators.CalculatorsFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.IncomeDailyFragment;
import HelperClasses.AlarmReceiver;
import HelperClasses.NotificationHelper;
import HelperClasses.ToolClass;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavigationView drawerView;
    private Toolbar toolbar;
    private TextView toolSeason;
    private DrawerLayout drawerLayout;
    private Fragment fragment =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        startSettings();
        createListeners();
//      createNotification();
//        test();

    }

    private void test() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.image_weed);
        Notification notification = new NotificationCompat.Builder(this, NotificationHelper.ID_OF_CHANNEL)
                .setColor(Color.BLUE)
                .setColorized(false)
                .setSmallIcon(R.drawable.image_attention)
                .setContentTitle("Zabieg chwastobójczy")
                .setContentText("O godzinie 16:00 zaplanowałeś zabieg chwastobójczy. Czas najwyższy rozpocząć przygotowania!")
                .setLargeIcon(picture)
                .setAutoCancel(true)
                .build();



        notificationManager.notify(1, notification);
    }

    private void createNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_MUTABLE);

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR,2022);
        c.set(Calendar.MONTH,6);
        c.set(Calendar.DAY_OF_MONTH,18);
        c.set(Calendar.HOUR_OF_DAY,15);
        c.set(Calendar.MINUTE,4);

        System.out.println(c.get(Calendar.DAY_OF_MONTH)+"."+c.get(Calendar.MONTH)+"."+c.get(Calendar.YEAR));


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tollbar_menu_default, menu);
        return true;
    }


    private void findViews() {
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        toolSeason=findViewById(R.id.tool_season);
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        drawerView=findViewById(R.id.drawer_view);

    }

    @SuppressLint("SetTextI18n")
    private void startSettings() {
        fragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        setSupportActionBar(toolbar);
        drawerView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolSeason.setText(toolSeason.getText()+" "+ ToolClass.getActualYear());
        ToolClass.clearTemporaryCurrentOperations(this);


    }

    @SuppressLint("NonConstantResourceId")
    private void createListeners() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            switch (id)
            {
                case R.id.bottom_farm:
                {
                    fragment=new FarmFragment();
                }break;
                case R.id.bottom_home:
                {
                    fragment=new HomeFragment();
                }break;
                case R.id.bottom_application:
                {
                    fragment=new AboutApplicationFragment();
                }break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.navCalculator:
            {
                fragment = new CalculatorsFragment();
            }break;
            case R.id.navIncomeNotes:
            {
                fragment = new IncomeDailyFragment();
            }break;
            case R.id.navOperations:
            {

            }break;
            case R.id.navNotes:
            {

            }break;
            case R.id.navImportantPlaces:
            {

            }break;
            case R.id.navClock:
            {

            }break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}