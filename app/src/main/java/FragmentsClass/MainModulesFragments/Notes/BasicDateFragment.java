package FragmentsClass.MainModulesFragments.Notes;

import static HelperClasses.ToolClass.generateStringDate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;

public class BasicDateFragment extends Fragment {

    private Context context;
    private TextView howSeeds, howPickling, howPlant, howFirstPepper, howLastPepper;
    private ImageView editSeedsButton, editPicklingButton, editPlantButton, editFirstPepperButton, editLastPepperButton;
    private ImageView buttonComeBack;

    private String date="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context = container.getContext();
        View view=inflater.inflate(R.layout.layout_basic_date_fragment,container,false);
        findViews(view);
        createListeners();
        loadData();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_basic_date));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howSeeds=view.findViewById(R.id.how_seeds);
        howPickling=view.findViewById(R.id.how_pickling);
        howPlant=view.findViewById(R.id.how_plant);
        howFirstPepper=view.findViewById(R.id.how_first_pepper);
        howLastPepper=view.findViewById(R.id.how_last_pepper);
        editSeedsButton=view.findViewById(R.id.edit_seeds_button);
        editPicklingButton=view.findViewById(R.id.edit_pickling_button);
        editPlantButton=view.findViewById(R.id.edit_plant_button);
        editFirstPepperButton=view.findViewById(R.id.edit_first_pepper_button);
        editLastPepperButton=view.findViewById(R.id.edit_last_pepper_button);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListeners() {
        View.OnClickListener listener = v -> {
            int id = v.getId();
            openChangeFarmDataDialog(id);
        };
        editSeedsButton.setOnClickListener(listener);
        editPicklingButton.setOnClickListener(listener);
        editPlantButton.setOnClickListener(listener);
        editFirstPepperButton.setOnClickListener(listener);
        editLastPepperButton.setOnClickListener(listener);

        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit());
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.BasicData.NAME,Context.MODE_PRIVATE);
        howSeeds.setText(sharedPreferences.getString(SharedPreferencesNames.BasicData.SEEDS,"-"));
        howPickling.setText(sharedPreferences.getString(SharedPreferencesNames.BasicData.PICKLING,"-"));
        howPlant.setText(sharedPreferences.getString(SharedPreferencesNames.BasicData.PLANT,"-"));
        howFirstPepper.setText(sharedPreferences.getString(SharedPreferencesNames.BasicData.FIRST_PEPPER,"-"));
        howLastPepper.setText(sharedPreferences.getString(SharedPreferencesNames.BasicData.LAST_PEPPER,"-"));
    }

    private void openChangeFarmDataDialog(int id) {
        Dialog changeDataDialog = new Dialog(context);
        changeDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        changeDataDialog.setContentView(R.layout.dialog_change_date);
        changeDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeDataDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        changeDataDialog.show();
        createAndAddListeners(changeDataDialog, id);
    }

    @SuppressLint("NonConstantResourceId")
    private void createAndAddListeners(Dialog changeDataDialog, int id) {
        CalendarView calendar = changeDataDialog.findViewById(R.id.calendar);
        Button btnAccept = changeDataDialog.findViewById(R.id.btn_accept);
        Button btnCancel = changeDataDialog.findViewById(R.id.btn_cancel);


        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.BasicData.NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        btnAccept.setOnClickListener(v -> {
                switch (id) {
                    case R.id.edit_seeds_button: {
                        editor.putString(SharedPreferencesNames.BasicData.SEEDS,date);
                    }break;
                    case R.id.edit_pickling_button: {
                        editor.putString(SharedPreferencesNames.BasicData.PICKLING,date);
                    }break;
                    case R.id.edit_plant_button: {
                        editor.putString(SharedPreferencesNames.BasicData.PLANT,date);
                        editor.putInt(SharedPreferencesNames.BasicData.IS_PLANT_EMPTY,1);
                    }break;
                    case R.id.edit_first_pepper_button:{
                        editor.putString(SharedPreferencesNames.BasicData.FIRST_PEPPER,date);
                    }break;
                    case R.id.edit_last_pepper_button:{
                        editor.putString(SharedPreferencesNames.BasicData.LAST_PEPPER,date);
                    }break;
                }
                editor.apply();
                changeDataDialog.dismiss();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BasicDateFragment()).commit();
        });

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> date=generateStringDate(dayOfMonth,month,year));

        btnCancel.setOnClickListener(v -> changeDataDialog.dismiss());
    }
}
