package FragmentsClass.MainModulesFragments.ControlOfWater;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.DropOfWaterAdapter;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.DropOfWaterItem;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.WateringAdapter;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.WateringItem;
import HelperClasses.InformationDialog;
import HelperClasses.temp.WateringService;

public class WaterPepperFragment extends Fragment {

    private Context context;
    private final ArrayList<DropOfWaterItem> dropOfWaterItems = new ArrayList<>();
    private RecyclerView recyclerView;

    private TextView titleOfRound, howHighgroves, howTime, howEfficiency, titleofWatering, time;
    private ImageView imageOfWatering;
    private Button defaultButton;

    AnimationDrawable animationDrawable;

    int id;

    String dots = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_water_pepper, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        startSettings();
        createListeners();
        startService();
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

        }
    };

    private void startService() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        ContextCompat.startForegroundService(context, wateringIntent);

        imageOfWatering.setBackgroundResource(R.drawable.animation_dropping_of_water);
        animationDrawable = (AnimationDrawable) imageOfWatering.getBackground();
        animationDrawable.start();

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
        recyclerView=view.findViewById(R.id.recycler_view);
        defaultButton=view.findViewById(R.id.default_button);
        titleofWatering=view.findViewById(R.id.title_of_watering);
        time=view.findViewById(R.id.time);
        imageOfWatering=view.findViewById(R.id.image_of_watering);

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_WATERING_RV, 0);
    }

    private void createListeners() {
        defaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServicee();
            }
        });

    }

    private void stopServicee() {
        Intent wateringIntent = new Intent(context, WateringService.class);
        context.stopService(wateringIntent);
        animationDrawable.stop();
    }



    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        DropOfWaterAdapter adapter = new DropOfWaterAdapter(dropOfWaterItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        dropOfWaterItems.add(new DropOfWaterItem(1,R.drawable.image_drop_of_water_main_color));
        dropOfWaterItems.add(new DropOfWaterItem(2,R.drawable.image_drop_of_water_main_color));
        dropOfWaterItems.add(new DropOfWaterItem(3,R.drawable.image_drop_of_water_blue));
        dropOfWaterItems.add(new DropOfWaterItem(4,R.drawable.image_drop_of_water));
        dropOfWaterItems.add(new DropOfWaterItem(5,R.drawable.image_drop_of_water));

    }
}


