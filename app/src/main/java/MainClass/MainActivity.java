package MainClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pracadyplomowa.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;

import FragmentsClass.BottomFragments.AboutApplicationFragment;
import FragmentsClass.BottomFragments.FarmFragment;
import FragmentsClass.BottomFragments.HomeFragment;
import FragmentsClass.MainModulesFragments.Calculators.CalculatorsFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.IncomeDailyFragment;

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
        startSettings(savedInstanceState);
        createListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tollbar_menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.information:
            {

            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        toolSeason=findViewById(R.id.tool_season);
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        drawerView=findViewById(R.id.drawer_view);

    }

    private void startSettings(Bundle savedInstanceState) {
        fragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        setSupportActionBar(toolbar);
        drawerView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Date date = new Date();
        toolSeason.setText(toolSeason.getText()+" "+String.valueOf(date.getYear()+1900));


    }

    private void createListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener listener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        }
    };

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