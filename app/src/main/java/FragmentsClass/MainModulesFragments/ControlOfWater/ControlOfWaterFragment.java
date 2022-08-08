package FragmentsClass.MainModulesFragments.ControlOfWater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.Notes.AddNoteFragment;
import FragmentsClass.MainModulesFragments.Notes.BasicDateFragment;
import FragmentsClass.MainModulesFragments.Notes.MyNotesFragment;
import HelperClasses.InformationDialog;

public class ControlOfWaterFragment extends Fragment {

    ConstraintLayout btnWaterPlantation, btnDailyOfWatering, btnMissWater;

    Fragment fragment;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_control_of_water, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
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

    private void findViews(View view) {
        btnWaterPlantation=view.findViewById(R.id.btn_water_plantation);
        btnDailyOfWatering=view.findViewById(R.id.btn_daily_of_watering);
        btnMissWater=view.findViewById(R.id.btn_miss_water);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_water_plantation:
                {
                    fragment = new WaterPlantationFragment();
                }break;
                case R.id. btn_daily_of_watering:
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.WateringData.NAME,Context.MODE_PRIVATE);
                    boolean mode = sharedPreferences.getBoolean(SharedPreferencesNames.WateringData.FIRST_SETTING, false);
                    if(mode)
                        fragment = new WaterPepperFragment();
                    else
                        fragment = new DailyOfWateringFragment();
                }break;
                case R.id.btn_miss_water:
                {
                    fragment = new UsagesOfWaterFragment();
                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnWaterPlantation.setOnClickListener(listener);
        btnDailyOfWatering.setOnClickListener(listener);
        btnMissWater.setOnClickListener(listener);
    }
}


