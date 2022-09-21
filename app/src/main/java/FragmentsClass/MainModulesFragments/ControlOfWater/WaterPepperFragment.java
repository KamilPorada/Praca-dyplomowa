package FragmentsClass.MainModulesFragments.ControlOfWater;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;
import HelperClasses.WateringHelperClasses.WateringService;

public class WaterPepperFragment extends Fragment {

    private Context context;
    private final ArrayList<DropOfWaterItem> dropOfWaterItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView titleOfRound, howHighgroves, howTime, howEfficiency, titleofWatering, time;
    private ImageView imageOfWatering, buttonComeBack, imageTap, imageValve, stopButton;
    private Button resumeButton;

    private AnimationDrawable animationDroppingWater;

    private String dots = "";
    private long timerTime;
    private boolean timerEnabled, firstSetting, secondSetting, thirdSetting, fourthSetting, fivethSetting;
    private int[] highgroves, times;
    private int id, actualRound, amountOfRound;
    private double efficiency;

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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_water_pepper));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter(new IntentFilter(WateringService.NAME_OF_BROADCAST_INTENT)));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            timerEnabled = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.TIMER_ENABLED,false);
            timerTime=intent.getLongExtra(SharedPreferencesNames.WateringData.TIME,0);
            time.setText(ToolClass.generateCountDownTimerTime(timerTime));

            editor.putBoolean(SharedPreferencesNames.WateringData.SECOND_SETTING, timerEnabled);
            editor.apply();
            loadData();
        }
    };

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

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);

        actualRound = sharedPreferences.getInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
        firstSetting = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.FIRST_SETTING,false);
        secondSetting = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.SECOND_SETTING,false);
        thirdSetting = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
        fourthSetting = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.FOURTH_SETTING,false);
        fivethSetting = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.FIVETH_SETTING,false);

        imageOfWatering.setBackgroundResource(R.drawable.animation_dropping_of_water);
        animationDroppingWater = (AnimationDrawable) imageOfWatering.getBackground();

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


        if(!firstSetting)   // START PODLEWANIE NIE ROZPOCZĘTE
        {
            titleofWatering.setText("Wciśnij przycisk aby rozpocząć!");
            time.setText(ToolClass.generateCountDownTimerTime(times[actualRound-1]* 60L));
            imageOfWatering.setVisibility(View.INVISIBLE);
            imageTap.setVisibility(View.VISIBLE);
            imageValve.setVisibility(View.INVISIBLE);
        }
        else               // PODLEWANIE ROZPOCZĘTE
        {
            if(secondSetting) // TRWA PODLEWANIE
            {
                titleofWatering.setText("Trwa podlewanie");
                imageOfWatering.setVisibility(View.VISIBLE);
                imageTap.setVisibility(View.VISIBLE);
                imageValve.setVisibility(View.INVISIBLE);
                resumeButton.setEnabled(false);
                resumeButton.setBackgroundColor(Color.GRAY);
                animationDroppingWater.start();
                dots=dots+".";
                titleofWatering.setText("Trwa podlewanie"+dots);
                if(dots.length()>3)
                    dots="";
            }
            else            //POMIĘDZYRUNDZIE
            {
                if(thirdSetting && !fivethSetting)   // PRZEKREC ZAWORY
                {
                    titleofWatering.setText("Przekreć zawory!");
                    time.setText("00:00");
                    imageOfWatering.setVisibility(View.INVISIBLE);
                    imageTap.setVisibility(View.INVISIBLE);
                    imageValve.setVisibility(View.VISIBLE);
                    resumeButton.setEnabled(false);
                    resumeButton.setBackgroundColor(Color.GRAY);
                    imageValve.setClickable(true);
                }
                else if(fourthSetting)  //NASTĘPNA RUNDA
                {
                    titleofWatering.setText("Wciśnij przycisk aby kontynuować!");
                    time.setText(ToolClass.generateCountDownTimerTime(times[actualRound-1]* 60L));
                    imageOfWatering.setVisibility(View.INVISIBLE);
                    imageTap.setVisibility(View.VISIBLE);
                    imageValve.setVisibility(View.INVISIBLE);
                    animationDroppingWater.stop();
                }
                else if(fivethSetting)  //WYLĄCZ POMPE
                {
                    titleofWatering.setText("Wyłącz pompę!");
                    time.setText("00:00");
                    imageOfWatering.setVisibility(View.INVISIBLE);
                    imageTap.setVisibility(View.INVISIBLE);
                    imageValve.setVisibility(View.VISIBLE);
                    imageValve.setImageResource(R.drawable.icon_power_off);
                    resumeButton.setEnabled(true);
                    resumeButton.setText("Zakończ");
                    resumeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_stop_circle,0,0,0);
                    actualRound++;
                    stopButton.setVisibility(View.INVISIBLE);
                    imageValve.setClickable(false);
                }
            }
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
            else if(i==actualRound && timerEnabled)
                dropOfWaterItems.add(new DropOfWaterItem(i,R.drawable.image_drop_of_water_blue));
            else if(i == actualRound)
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
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ControlOfWaterFragment()).commit());

        stopButton.setOnClickListener(v -> openAttentionDialog());

        resumeButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(actualRound==1) {
                editor.putBoolean(SharedPreferencesNames.WateringData.FIRST_SETTING, true);
                editor.apply();
            }
            if(resumeButton.getText().toString().compareTo("Zakończ")==0)
            {
                ShowToast toast = new ShowToast();
                toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie zakończyłeś nawadnianie!");
                editor.putInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
                editor.putBoolean(SharedPreferencesNames.WateringData.FIRST_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.SECOND_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.FOURTH_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.FIVETH_SETTING,false);
                editor.putInt(SharedPreferencesNames.WateringData.AMOUNT_OF_ROUND,amountOfRound);
                editor.apply();
                Intent wateringIntent = new Intent(context, WateringService.class);
                context.stopService(wateringIntent);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                dataBaseHelper.updateSpecifyWatering(id, 1);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ControlOfWaterFragment()).commit();
            }
            else {
                resumeButton.setEnabled(false);
                resumeButton.setBackgroundColor(Color.GRAY);
                editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.FOURTH_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.FIVETH_SETTING,false);
                editor.apply();
                    if(actualRound==amountOfRound)
                    {
                        editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
                        editor.putBoolean(SharedPreferencesNames.WateringData.FOURTH_SETTING,false);
                        editor.putBoolean(SharedPreferencesNames.WateringData.FIVETH_SETTING,true);
                        editor.apply();
                    }
                startService();
            }
        });

        imageValve.setOnClickListener(v -> {
            if(imageValve.getVisibility()==View.VISIBLE )
            {
                resumeButton.setEnabled(true);
                resumeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.mainColor));
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
                editor.putBoolean(SharedPreferencesNames.WateringData.FOURTH_SETTING,true);
                editor.putBoolean(SharedPreferencesNames.WateringData.FIVETH_SETTING,false);
                editor.putInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, ++actualRound);
                editor.apply();
                loadData();
            }
        });
    }

    private void startService() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        wateringIntent.putExtra(SharedPreferencesNames.WateringData.TIME, times[actualRound-1]);
        ContextCompat.startForegroundService(context, wateringIntent);
        animationDroppingWater.start();
        refreshRecyclerView();
    }

    private void openAttentionDialog() {
        Dialog attentionDialog = new Dialog(context);
        attentionDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        attentionDialog.setContentView(R.layout.dialog_attention);
        attentionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        attentionDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        attentionDialog.show();
        createDialogListenerss(attentionDialog);
    }

    private void createDialogListenerss(Dialog attentionDialog) {
        Button stopButton = attentionDialog.findViewById(R.id.stop_button);
        Button comeBackButton = attentionDialog.findViewById(R.id.come_back_button);

        stopButton.setOnClickListener(v -> {
            ShowToast toast = new ShowToast();
            Intent wateringIntent = new Intent(context, WateringService.class);
            context.stopService(wateringIntent);
            SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SharedPreferencesNames.WateringData.FIRST_SETTING,false);
            editor.putBoolean(SharedPreferencesNames.WateringData.SECOND_SETTING,false);
            editor.putBoolean(SharedPreferencesNames.WateringData.THIRD_SETTING,false);
            editor.putBoolean(SharedPreferencesNames.WateringData.FOURTH_SETTING,false);
            editor.putBoolean(SharedPreferencesNames.WateringData.FIVETH_SETTING,false);
            editor.putInt(SharedPreferencesNames.WateringData.ACTUAL_ROUND, 1);
            editor.apply();
            attentionDialog.dismiss();
            timerEnabled=false;
            toast.showErrorToast(context, "UWAGA!\n" + "  Przerwałeś nawadnianie plantacji!", R.drawable.icon_drop_of_water);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ControlOfWaterFragment()).commit();
        });
        comeBackButton.setOnClickListener(v -> attentionDialog.dismiss());
    }
}


