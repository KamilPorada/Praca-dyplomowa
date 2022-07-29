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
    private ImageView imageOfWatering, buttonComeBack;
    private ImageButton resumeButton, pauseButton, stopButton;

    private AnimationDrawable animationDroppingWater;

    private int[] highgroves, times;
    private int id, actualRound, actualHighgroces, actualTime, amountOfRound;
    private double efficiency;

    private String dots = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_water_pepper, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        startSettings();
        createListeners();
        return view;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long millisUntilFinished = intent.getLongExtra("time", 0)/1000;
            String stringTime = "";
            if(millisUntilFinished/60 < 10)
                stringTime = stringTime + "0" + millisUntilFinished/60 + ":";
            else
                stringTime = stringTime + millisUntilFinished/60 + ":";
            if(millisUntilFinished%60 < 10)
                stringTime = stringTime + "0" + millisUntilFinished%60;
            else
                stringTime = stringTime + millisUntilFinished%60;
            time.setText(stringTime);

            dots=dots+".";
            titleofWatering.setText("Trwa podlewanie"+dots);
            if(dots.length()>4)
                dots="";

            boolean end = intent.getBooleanExtra("end", false);

            if (end)
                stopServicee();


        }
    };

    private void startService() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        ContextCompat.startForegroundService(context, wateringIntent);

        animationDroppingWater.start();

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



    private void findViews(View view) {
        titleOfRound=view.findViewById(R.id.title_of_round);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        howTime=view.findViewById(R.id.how_time);
        howEfficiency=view.findViewById(R.id.how_efficiency);
        titleofWatering=view.findViewById(R.id.title_of_watering);
        time=view.findViewById(R.id.time);
        imageOfWatering=view.findViewById(R.id.image_of_watering);
        buttonComeBack=view.findViewById(R.id.button_come_back);
        resumeButton=view.findViewById(R.id.resume_button);
        pauseButton=view.findViewById(R.id.pause_button);
        stopButton=view.findViewById(R.id.stop_button);
        recyclerView=view.findViewById(R.id.recycler_view);


        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_WATERING_RV, 0);
    }

    private void loadData() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        Cursor k = dataBaseHelper.getSpecifyWateringPlantationValues(id);
        k.moveToFirst();

        amountOfRound = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_ROUND));
        efficiency = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP));
        highgroves= ToolClass.separateString(k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND)));
        times=ToolClass.separateString(k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND)));

    }

    private void createListeners() {
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServicee();
            }
        });
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

    }

    private void stopServicee() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        context.stopService(wateringIntent);
        animationDroppingWater.stop();
        titleofWatering.setText("Tura zakończona");
    }



    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        DropOfWaterAdapter adapter = new DropOfWaterAdapter(dropOfWaterItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        imageOfWatering.setBackgroundResource(R.drawable.animation_dropping_of_water);
        animationDroppingWater = (AnimationDrawable) imageOfWatering.getBackground();

        dropOfWaterItems.add(new DropOfWaterItem(1,R.drawable.image_drop_of_water_main_color));
        dropOfWaterItems.add(new DropOfWaterItem(2,R.drawable.image_drop_of_water_main_color));
        dropOfWaterItems.add(new DropOfWaterItem(3,R.drawable.image_drop_of_water_blue));
        dropOfWaterItems.add(new DropOfWaterItem(4,R.drawable.image_drop_of_water));
        dropOfWaterItems.add(new DropOfWaterItem(5,R.drawable.image_drop_of_water));

    }
}


