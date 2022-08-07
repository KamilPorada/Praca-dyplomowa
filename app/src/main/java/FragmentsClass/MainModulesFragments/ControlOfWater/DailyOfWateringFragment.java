package FragmentsClass.MainModulesFragments.ControlOfWater;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.WateringAdapter;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.WateringItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class DailyOfWateringFragment extends Fragment {
    
    private Context context;
    private final ArrayList<WateringItem> wateringItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView buttonComeBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_daily_of_watering, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListener();
        startSettings();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_outgoings));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListener() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ControlOfWaterFragment()).commit());
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        WateringAdapter adapter = new WateringAdapter(wateringItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new WateringAdapter.OnItemClickListener() {
            @Override
            public void onDoneClick(int position) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_WATERING_RV, wateringItems.get(position).getIId());
                editor.apply();


                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WaterPepperFragment()).commit();
            }

            @Override
            public void onDeleteClick(int position) {
                openDialogQuestion(position);
            }
        });
    }


    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getWateringPlantationItems();
        while(k.moveToNext()) {
            double efficiency = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_EFFICIENCY_OF_PUMP));
            String stringHighgroves = k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_AMOUNT_OF_HIGHGROVES_IN_EACH_ROUND));
            String date = k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_DATE));
            String stringTimes = k.getString(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_TIMES_OF_EACH_ROUND));
            int status = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem.COLUMN_STATUS));
            int id = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.WaterPlantationItem._ID));
            String  highgroves = String.valueOf(ToolClass.sumString(stringHighgroves));
            int minutes = ToolClass.sumString(stringTimes);
            String stringTime = ToolClass.getStringTime(minutes);
            String usageOfWater = (int) efficiency * minutes + " l";

            if(ToolClass.getActualYear()==ToolClass.getYear(date))
                if(status==0)
                    wateringItems.add(new WateringItem(id, efficiency, date, highgroves, stringTime, usageOfWater, status));
        }

    }

    // ------------------------DELETE ITEM WINDOW EVENTS---------------------------//

    private void openDialogQuestion(int position) {
        Dialog questionWindow = new Dialog(context);
        questionWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        questionWindow.setContentView(R.layout.dialog_question);
        questionWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        questionWindow.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        questionWindow.show();
        createAndAddListener(questionWindow, position);
    }


    private void createAndAddListener(Dialog questionWindow, int position) {
        Button cancelButton = questionWindow.findViewById(R.id.cancel_button);
        Button deleteButton = questionWindow.findViewById(R.id.delete_button);
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.cancel_button:
                {
                    questionWindow.dismiss();
                }break;
                case R.id.delete_button:
                {
                    deleteItem(position);
                    questionWindow.dismiss();
                }break;
            }
        };
        cancelButton.setOnClickListener(listener);
        deleteButton.setOnClickListener(listener);
    }

    private void deleteItem(int position) {
        DataBaseHelper db = new DataBaseHelper(context);
        db.deleteItem(DataBaseNames.WaterPlantationItem.TABLE_NAME,wateringItems.get(position).getIId());
        ShowToast toast = new ShowToast();
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie usunąłeś pozycję nawadniania!");
        Fragment fragment = new DailyOfWateringFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }
}


