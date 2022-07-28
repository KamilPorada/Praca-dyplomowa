package FragmentsClass.MainModulesFragments.ControlOfWater;

import static HelperClasses.ToolClass.generateStringDate;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.RoundAdapter;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses.RoundItem;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerItem;
import FragmentsClass.MainModulesFragments.Operations.CatalogOfOperationsFragment;
import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfPesticideAdapter;
import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfPesticideItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class WaterPlantationFragment extends Fragment {

    private Context context;
    private final ArrayList<RoundItem> roundItems = new ArrayList<>();

    private TextInputEditText howEfficiency, howDate;
    private ImageButton saveEfficiencyButton, editDateButton, addRoundButton;
    private RecyclerView recyclerView;
    private Button buttonAccept, buttonCancel;

    private int roundsOfWatering=1;
    private int highgroves=0;
    private String calendarDate="", amountOfHighgrovesInEachRound="", timesOfEachRound="";
    private Boolean save=true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_water_plantation, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
        //startSettings();
        loadData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("NonConstantResourceId")
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
        howEfficiency=view.findViewById(R.id.how_efficiency);
        howDate=view.findViewById(R.id.how_date);
        saveEfficiencyButton=view.findViewById(R.id.save_efficiency_button);
        editDateButton=view.findViewById(R.id.edit_date_button);
        addRoundButton=view.findViewById(R.id.add_round_button);
        recyclerView=view.findViewById(R.id.recycler_view);
        buttonAccept=view.findViewById(R.id.button_accept);
        buttonCancel=view.findViewById(R.id.button_cancel);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        RoundAdapter adapter = new RoundAdapter(roundItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void createListeners() {

        View.OnClickListener imageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id =v.getId();
                switch (id)
                {
                    case R.id.save_efficiency_button:
                    {
                        saveEfficiencyOfPump();
                    }break;
                    case R.id.edit_date_button:
                    {
                        openEditDataDialog();
                    }break;
                    case R.id.add_round_button:
                    {
                        openAddTimeAndHighgroveDialog();
                    }break;
                }
            }
        };

        saveEfficiencyButton.setOnClickListener(imageListener);
        editDateButton.setOnClickListener(imageListener);
        addRoundButton.setOnClickListener(imageListener);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id)
                {
                    case R.id.button_accept:
                    {
                        validateData();
                    }break;
                    case R.id.button_cancel:
                    {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ControlOfWaterFragment()).commit();
                    }break;
                }
            }
        };

        buttonAccept.setOnClickListener(buttonListener);
        buttonCancel.setOnClickListener(buttonListener);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        String stringEfficiency = String.valueOf(sharedPreferences.getFloat(SharedPreferencesNames.ToolSharedPreferences.EFFICIENCY_OF_PUMP,0.0f));
        if(stringEfficiency.compareTo("0.0")!=0){
            howEfficiency.setText(String.valueOf(sharedPreferences.getFloat(SharedPreferencesNames.ToolSharedPreferences.EFFICIENCY_OF_PUMP,0.0f)));
            howEfficiency.setEnabled(false);
            saveEfficiencyButton.setImageResource(R.drawable.icon_edit);
            save=false;
        }
    }

    private void saveEfficiencyOfPump() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!save)
        {
            howEfficiency.setEnabled(true);
            saveEfficiencyButton.setImageResource(R.drawable.icon_save);
            save=true;
        }
        else
        {
            if(howEfficiency.getText().toString().compareTo("")!=0){
                editor.putFloat(SharedPreferencesNames.ToolSharedPreferences.EFFICIENCY_OF_PUMP, Float.parseFloat(howEfficiency.getText().toString()));
                editor.apply();
                howEfficiency.setEnabled(false);
                saveEfficiencyButton.setImageResource(R.drawable.icon_edit);
                save=false;
            }
        }

    }

    private void openEditDataDialog() {
        Dialog editDataDialog = new Dialog(context);
        editDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        editDataDialog.setContentView(R.layout.dialog_change_date);
        editDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDataDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editDataDialog.show();
        createDialogListeners(editDataDialog);
    }

    private void createDialogListeners(Dialog editDataDialog) {
        CalendarView calendar;
        Button btnAccept, btnCancel;

        calendar=editDataDialog.findViewById(R.id.calendar);
        btnAccept=editDataDialog.findViewById(R.id.btn_accept);
        btnCancel=editDataDialog.findViewById(R.id.btn_cancel);

        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_accept:
                {
                    howDate.setText(calendarDate);
                    editDataDialog.dismiss();
                }break;
                case R.id.btn_cancel:
                {
                    editDataDialog.dismiss();
                }break;
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> calendarDate=generateStringDate(dayOfMonth,month,year));
    }

    private void openAddTimeAndHighgroveDialog() {
        Dialog addTimeAndHighgroveDialog = new Dialog(context);
        addTimeAndHighgroveDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        addTimeAndHighgroveDialog.setContentView(R.layout.dialog_add_time_and_highgroves);
        addTimeAndHighgroveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addTimeAndHighgroveDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        addTimeAndHighgroveDialog.show();
        createDialogListenerss(addTimeAndHighgroveDialog);
    }

    private void createDialogListenerss(Dialog addTimeAndHighgroveDialog) {
        TextInputEditText howHighgroves = addTimeAndHighgroveDialog.findViewById(R.id.how_highgroves);
        TextInputEditText howTime = addTimeAndHighgroveDialog.findViewById(R.id.how_time);
        Button acceptButton = addTimeAndHighgroveDialog.findViewById(R.id.accept_button);
        Button cancelButton = addTimeAndHighgroveDialog.findViewById(R.id.cancel_button);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast toast = new ShowToast();
                int id = v.getId();
                switch (id)
                {
                    case R.id.cancel_button:
                    {
                        addTimeAndHighgroveDialog.dismiss();
                    }break;
                    case R.id.accept_button:
                    {
                        if(howHighgroves.getText().toString().compareTo("")!=0 &&
                           howTime.getText().toString().compareTo("")!=0)
                        {
                            if(highgroves+Integer.parseInt(howHighgroves.getText().toString())>ToolClass.getHighgroves(context))
                                toast.showErrorToast(context, "Zbyt duża liczba tuneli!\n" + "  Posiadasz " + (int)ToolClass.getHighgroves(context) + " tuneli foliowych!", R.drawable.icon_information);
                            else {
                                amountOfHighgrovesInEachRound = amountOfHighgrovesInEachRound + howHighgroves.getText().toString() + "|";
                                timesOfEachRound = timesOfEachRound + howTime.getText().toString() + "|";
                                roundItems.add(new RoundItem(roundsOfWatering, howHighgroves.getText().toString(), howTime.getText().toString()));
                                roundsOfWatering++;
                                highgroves=highgroves+Integer.parseInt(howHighgroves.getText().toString());
                                recyclerView.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                RoundAdapter adapter = new RoundAdapter(roundItems);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                        addTimeAndHighgroveDialog.dismiss();
                    }break;
                }
            }
        };
        cancelButton.setOnClickListener(listener);
        acceptButton.setOnClickListener(listener);
    }

    private void validateData() {
        ShowToast toast = new ShowToast();
        if(Objects.requireNonNull(howDate.getText()).toString().compareTo("")==0 || Objects.requireNonNull(howEfficiency.getText()).toString().compareTo("")==0)
                toast.showErrorToast(context, "BŁĄD DANYCH!\n"+"  Uzupełnij wszystkie pola!", R.drawable.icon_information);
        else {
            if(ToolClass.checkValidateData(howDate.getText().toString()))
                if(ToolClass.checkValidateYear(howDate.getText().toString()))
                    if(Objects.requireNonNull(amountOfHighgrovesInEachRound.compareTo("")!=0))
                        addItem();
                    else
                        toast.showErrorToast(context, "Brak dodanych tur!\n" + "  Dodaj turę podlewania!", R.drawable.image_drop_of_water);
                else
                    toast.showErrorToast(context, "Podaj poprawny rok!\n" + "  Mamy aktualnie " + ToolClass.getActualYear() + " rok!", R.drawable.icon_calendar);
            else
                toast.showErrorToast(context, "Błędny format daty!\n" + "  [dd.mm.rrrr]", R.drawable.icon_calendar);
        }
    }

    private void addItem() {
        ShowToast toast = new ShowToast();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.addWaterPlantation(Double.parseDouble(howEfficiency.getText().toString()), howDate.getText().toString(),
                                          amountOfHighgrovesInEachRound, timesOfEachRound, 0);
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie zaplanowałeś podlewanie plantacji!");
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ControlOfWaterFragment()).commit();

    }

}


