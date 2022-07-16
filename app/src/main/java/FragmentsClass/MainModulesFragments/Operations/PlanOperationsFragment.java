package FragmentsClass.MainModulesFragments.Operations;

import static HelperClasses.ToolClass.getActualYear;

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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DataBase.DataBaseHelper;
import HelperClasses.InformationDialog;
import HelperClasses.ShowAttention;
import HelperClasses.ToolClass;

public class PlanOperationsFragment extends Fragment {


    private Fragment fragment = null;
    private Context context;

    private RadioButton insecticidies, fungicidies, herbicidies;
    private TextInputEditText howDate, howHour;
    private TextView howAge, howPesticide, titleHighgroves;
    private SeekBar howHighgroves;
    private ImageButton editDateButton, editHourButton, addPesticideButton;
    private Button planOperationButton, cancelButton;

    private String date, hour, pesticides;
    private int highgroves, age=90, typeOfPesticides=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_plan_operations_fragment, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
        return view;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TEMPORARY_CURRENT_OPERATIONS",Context.MODE_PRIVATE);
        int checkedRadio = sharedPreferences.getInt("TYPE_OF_PESTICIDES", 0);
        howDate.setText(sharedPreferences.getString("DATA_OF_OPERATIONS",""));
        howHour.setText(sharedPreferences.getString("HOUR_OF_OPERATIONS", ""));
        howHighgroves.setProgress(sharedPreferences.getInt("AMOUNT_OF_HIGHGROVES",0));
        titleHighgroves.setText("Ilość tuneli do opryskania: " + sharedPreferences.getInt("AMOUNT_OF_HIGHGROVES",0));
        howPesticide.setText(sharedPreferences.getString("PESTICIDES", ""));
        switch (checkedRadio)
        {
            case 0:
            {
                insecticidies.setChecked(true);
            }break;
            case 1:
            {
                fungicidies.setChecked(true);
            }break;
            case 2:
            {
                herbicidies.setChecked(true);
            }break;
        }
        if(howPesticide.getText().toString().compareTo("")!=0)
        {
            addPesticideButton.setEnabled(false);
            insecticidies.setEnabled(false);
            fungicidies.setEnabled(false);
            herbicidies.setEnabled(false);
        }
        else
        {
            addPesticideButton.setEnabled(true);
            insecticidies.setEnabled(true);
            fungicidies.setEnabled(true);
            herbicidies.setEnabled(true);
        }
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculators));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        insecticidies=view.findViewById(R.id.insecticidies);
        fungicidies=view.findViewById(R.id.fungicidies);
        herbicidies=view.findViewById(R.id.herbicidies);
        howDate=view.findViewById(R.id.date);
        howHour=view.findViewById(R.id.hour);
        howAge=view.findViewById(R.id.how_age);
        howPesticide=view.findViewById(R.id.how_pesticides);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        titleHighgroves=view.findViewById(R.id.title_highgroves);
        editDateButton=view.findViewById(R.id.edit_date_button);
        editHourButton=view.findViewById(R.id.edit_hour_button);
        addPesticideButton=view.findViewById(R.id.add_pesticide_button);
        planOperationButton=view.findViewById(R.id.button_plan_operations);
        cancelButton=view.findViewById(R.id.button_cancel);
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.edit_date_button:
                    {
                        openEditDataDialog();
                    }break;
                    case R.id.edit_hour_button:
                    {
                        openEditHourDialog();
                    }break;
                    case R.id.add_pesticide_button:
                    {
                        openCatalogOfPesticide();
                    }break;
                    case R.id.button_plan_operations:
                    {
                        validateData();
                    }break;
                    case R.id.button_cancel:
                    {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OperationsFragment()).commit();
                    }break;
                }
            }
        };
        editDateButton.setOnClickListener(listener);
        editHourButton.setOnClickListener(listener);
        addPesticideButton.setOnClickListener(listener);
        planOperationButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        howHighgroves.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                titleHighgroves.setText("Ilość tuneli do opryskania: " + progress);
                highgroves=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        howHighgroves.setMax((int) ToolClass.getHighgroves(context));
    }

    private void openCatalogOfPesticide() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("CATALOG_OF_PESTICIDE_OPEN_MODE", 0);
        editor.apply();
        sharedPreferences = context.getSharedPreferences("TEMPORARY_CURRENT_OPERATIONS",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(insecticidies.isChecked())
            typeOfPesticides=0;
        else if(fungicidies.isChecked())
            typeOfPesticides=1;
        else if(herbicidies.isChecked())
            typeOfPesticides=2;
        editor.putInt("TYPE_OF_PESTICIDES", typeOfPesticides);
        editor.putString("DATA_OF_OPERATIONS", howDate.getText().toString());
        editor.putString("HOUR_OF_OPERATIONS", howHour.getText().toString());
        editor.putInt("AMOUNT_OF_HIGHGROVES", highgroves);
        editor.putString("PESTICIDES", howPesticide.getText().toString());
        editor.putInt("ID_OF_PESTICIDES", 0);
        editor.putInt("GRACE", 0);
        editor.apply();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfPesticidesFragment()).commit();

    }

    private void openEditHourDialog() {
        Dialog editHourDialog = new Dialog(context);
        editHourDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        editHourDialog.setContentView(R.layout.dialog_change_hour_of_operations);
        editHourDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editHourDialog.show();
        createDialoggListeners(editHourDialog);
    }

    private void createDialoggListeners(Dialog editHourDialog) {
        TimePicker clock;
        Button btnAccept, btnCancel;

        clock=editHourDialog.findViewById(R.id.clock);
        btnAccept=editHourDialog.findViewById(R.id.btn_accept);
        btnCancel=editHourDialog.findViewById(R.id.btn_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_accept:
                    {
                        howHour.setText(hour);
                        editHourDialog.dismiss();
                    }break;
                    case R.id.btn_cancel:
                    {
                        editHourDialog.dismiss();
                    }break;
                }
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        clock.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String clockHour="";
                if(hourOfDay<10)
                    clockHour=clockHour+"0"+hourOfDay+":";
                else
                    clockHour=clockHour+hourOfDay+":";
                if(minute<10)
                    clockHour=clockHour+"0"+minute;
                else
                    clockHour=clockHour+minute;

                hour=clockHour;
            }
        });

    }

    private void openEditDataDialog() {
        Dialog editDataDialog = new Dialog(context);
        editDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        editDataDialog.setContentView(R.layout.dialog_change_date_of_operations);
        editDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDataDialog.show();
        createDialogListeners(editDataDialog);
    }

    private void createDialogListeners(Dialog editDataDialog) {
        CalendarView calendar;
        Button btnAccept, btnCancel;

        calendar=editDataDialog.findViewById(R.id.calendar);
        btnAccept=editDataDialog.findViewById(R.id.btn_accept);
        btnCancel=editDataDialog.findViewById(R.id.btn_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_accept:
                    {
                        howDate.setText(date);
                        editDataDialog.dismiss();
                    }break;
                    case R.id.btn_cancel:
                    {
                        editDataDialog.dismiss();
                    }break;
                }
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String calendarDate="";
                if(dayOfMonth<10)
                    calendarDate=calendarDate+"0"+dayOfMonth+".";
                else
                    calendarDate=calendarDate+dayOfMonth+".";
                if(month+1<10)
                    calendarDate=calendarDate+"0"+(month+1)+".";
                else
                    calendarDate=calendarDate+(month+1)+".";
                calendarDate=calendarDate+year;
                date=calendarDate;
            }
        });

    }

    private void validateData() {
        ShowAttention showAttention = new ShowAttention();
        boolean checkDate=false;
        boolean checkHour=false;

        date = howDate.getText().toString();
        hour = howHour.getText().toString();
        pesticides = howPesticide.getText().toString();

        if (date.compareTo("") == 0 || hour.compareTo("") == 0 || pesticides.compareTo("") == 0)
            showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Uzupełnij wszystkie pola!");
        else {
            if (ToolClass.checkValidateData(howDate.getText().toString()))
                if(ToolClass.compareDateAndTimeWithCurrentDateAndTime(howDate.getText().toString(), howHour.getText().toString()))
                    if (ToolClass.checkValidateYear(howDate.getText().toString()))
                        checkDate = true;
                    else {
                        showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Podaj poprawny rok!\nMamy aktualnie " + getActualYear() + " rok!");
                        checkDate = false;
                    }
                else
                {
                    showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Podaj przyszłą datę!");
                    checkDate = false;
                }
            else {
                showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Zły format daty!\n[dd.mm.rrrr]");
                checkDate = false;
            }

            if (ToolClass.checkValidateHour(hour))
                checkHour = true;
            else {
                showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Zły format godziny!\n[gg:mm]");
                checkHour = false;
            }
        }

        if(checkDate && checkHour)
            addOperationsToDataBase();
    }

    private void addOperationsToDataBase() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TEMPORARY_CURRENT_OPERATIONS",Context.MODE_PRIVATE);
        int grace = sharedPreferences.getInt("GRACE", 0);
        int id = sharedPreferences.getInt("ID_OF_PESTICIDES", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,ToolClass.getDay(date));
        calendar.set(Calendar.MONTH,ToolClass.getMonth(date));
        calendar.set(Calendar.YEAR,ToolClass.getYear(date));

        calendar.add(Calendar.DAY_OF_MONTH,grace);
        String dateEndOfGrace="";

        if(calendar.get(Calendar.DAY_OF_MONTH)<10)
            dateEndOfGrace=dateEndOfGrace+"0"+calendar.get(Calendar.DAY_OF_MONTH)+".";
        else
            dateEndOfGrace=dateEndOfGrace+calendar.get(Calendar.DAY_OF_MONTH)+".";
        if(calendar.get(Calendar.MONTH)<10)
            dateEndOfGrace=dateEndOfGrace+"0"+(calendar.get(Calendar.MONTH))+".";
        else
            dateEndOfGrace=dateEndOfGrace+(calendar.get(Calendar.MONTH))+".";
        dateEndOfGrace=dateEndOfGrace+calendar.get(Calendar.YEAR);

        int fluid=0;
        if(insecticidies.isChecked())
            typeOfPesticides=0;
        else if(fungicidies.isChecked())
            typeOfPesticides=1;
        else if(herbicidies.isChecked())
            typeOfPesticides=2;
        if(typeOfPesticides==2)
            fluid=highgroves*5;
        else {
            if (age < 25)
                fluid = highgroves * 5;
            else if (age < 50)
                fluid = highgroves * 10;
            else if (age < 75)
                fluid = highgroves * 15;
            else
                fluid = highgroves * 20;
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.addOperation(id,date, hour, dateEndOfGrace, age, highgroves, fluid,0);

        ToolClass.clearTemporaryCurrentOperations(context);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OperationsFragment()).commit();


    }
}


