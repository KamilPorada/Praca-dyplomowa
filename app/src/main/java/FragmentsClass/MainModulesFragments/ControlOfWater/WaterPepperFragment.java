package FragmentsClass.MainModulesFragments.ControlOfWater;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.DropOfWaterAdapter;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.DropOfWaterItem;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.WateringAdapter;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.WateringItem;
import HelperClasses.InformationDialog;
import HelperClasses.ToolClass;
import HelperClasses.temp.WateringService;

public class WaterPepperFragment extends Fragment {

    private Context context;
    private final ArrayList<DropOfWaterItem> dropOfWaterItems = new ArrayList<>();
    private RecyclerView recyclerView;

    private TextView titleOfRound, howHighgroves, howTime, howEfficiency, titleofWatering, time;
    private ImageView imageOfWatering, buttonComeBack, imageTap, imageValve, stopButton;
    private Button resumeButton;

    private AnimationDrawable animationDroppingWater;

    private int[] highgroves, times;
    private int id, actualRound, actualHighgroces, actualTime, amountOfRound;
    private double efficiency;

    private long timerTime;
    private boolean timerEnabled, wateringEnabled, roundEnabled;

    private String dots = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_water_pepper, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_income_daily));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(new IntentFilter(WateringService.NAME_OF_BROADCAST_INTENT)));
    }

            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            timerTime=intent.getLongExtra(SharedPreferencesNames.WateringData.TIME,0);
            timerEnabled=intent.getBooleanExtra(SharedPreferencesNames.WateringData.TIMER_ENABLED,true);

            time.setText(ToolClass.generateCountDownTimerTime(timerTime));

            dots=dots+".";
            titleofWatering.setText("Trwa podlewanie"+dots);
            if(dots.length()>3)
                dots="";


            if (!timerEnabled) {
                stopServicee();
                titleofWatering.setText("Przekreć zawory!");
                imageOfWatering.setVisibility(View.INVISIBLE);
                imageTap.setVisibility(View.INVISIBLE);
                imageValve.setVisibility(View.VISIBLE);
            }
        }
    };

    private void startService() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        wateringIntent.putExtra(SharedPreferencesNames.WateringData.TIME, times[actualRound-1]);
        ContextCompat.startForegroundService(context, wateringIntent);
        animationDroppingWater.start();
        refreshRecyclerView();
    }

    private void stopServicee() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        context.stopService(wateringIntent);
        animationDroppingWater.stop();
    }


    private void findViews(View view) {
        titleOfRound=view.findViewById(R.id.title_of_round);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        howTime=view.findViewById(R.id.how_time);
        howEfficiency=view.findViewById(R.id.how_efficiency);
        titleofWatering=view.findViewById(R.id.title_of_watering);
        time=view.findViewById(R.id.time);
        imageOfWatering=view.findViewById(R.id.image_of_watering);
        buttonComeBack=view.findViewById(R.id.button_come_back);
        imageTap=view.findViewById(R.id.image_tap);
        imageValve=view.findViewById(R.id.image_valve);
        resumeButton=view.findViewById(R.id.resume_button);
        stopButton=view.findViewById(R.id.stop_button);
        recyclerView=view.findViewById(R.id.recycler_view);


        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_WATERING_RV, 0);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        actualRound = sharedPreferences.getInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
        wateringEnabled = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.WATERING_ENABLED, false);
        roundEnabled = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED,false);

        imageOfWatering.setBackgroundResource(R.drawable.animation_dropping_of_water);
        animationDroppingWater = (AnimationDrawable) imageOfWatering.getBackground();

//        editor.putInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
//        editor.apply();

//        if(roundEnabled)
//            animationDroppingWater.start();


        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        Cursor k = dataBaseHelper.getSpecifyWateringPlantationValues(id);
        k.moveToFirst();

        amountOfRound = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_ROUND));
        efficiency = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP));
        highgroves= ToolClass.separateString(k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND)));
        times=ToolClass.separateString(k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND)));

        titleOfRound.setText("TURA " + actualRound);
        howHighgroves.setText(String.valueOf(highgroves[actualRound-1]));
        howTime.setText(times[actualRound - 1] + " min");
        howEfficiency.setText((int) efficiency + " l/min");

        if(times[actualRound - 1]<10)
            time.setText("0" + times[actualRound - 1] + ":00");
        else
            time.setText(times[actualRound - 1] + ":00");

        if(!wateringEnabled)
        {
            sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
            editor.putInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
            editor.apply();
            actualRound=sharedPreferences.getInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
        }
        refreshRecyclerView();
    }

    public void refreshRecyclerView()
    {
        dropOfWaterItems.clear();

        for(int i=1;i<=amountOfRound;i++)
        {
            if(i<actualRound)
                dropOfWaterItems.add(new DropOfWaterItem(i,R.drawable.image_drop_of_water_main_color));
            else if(i==actualRound && roundEnabled)
                dropOfWaterItems.add(new DropOfWaterItem(i,R.drawable.image_drop_of_water_blue));
            else if(i==actualRound && !roundEnabled)
                dropOfWaterItems.add(new DropOfWaterItem(i,R.drawable.image_drop_of_water));
            else if(i>actualRound)
                dropOfWaterItems.add(new DropOfWaterItem(i,R.drawable.image_drop_of_water));
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        DropOfWaterAdapter adapter = new DropOfWaterAdapter(dropOfWaterItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void createListeners() {
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED, true);
                editor.putBoolean(SharedPreferencesNames.WateringData.WATERING_ENABLED, true);
                editor.apply();
                wateringEnabled = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.WATERING_ENABLED, false);
                roundEnabled = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.ROUND_ENABLED,false);

                startService();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServicee();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WaterPepperFragment()).commit();
            }
        });

        imageValve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageValve.getVisibility()==View.VISIBLE)
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, ++actualRound);
                    editor.apply();
                    titleofWatering.setText("Trwa podlewanie");
                    imageOfWatering.setVisibility(View.VISIBLE);
                    imageTap.setVisibility(View.VISIBLE);
                    imageValve.setVisibility(View.INVISIBLE);
                    loadData();
                }
            }
        });
    }
}


