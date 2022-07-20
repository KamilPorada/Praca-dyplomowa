package FragmentsClass.MainModulesFragments.Operations;

import static HelperClasses.ToolClass.generateStringDate;
import static HelperClasses.ToolClass.getActualYear;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

import DataBase.DataBaseHelper;
import HelperClasses.AlarmReceiver;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class PlanOperationsFragment extends Fragment {

    private Context context;
    private RadioButton insecticidies, fungicidies, herbicidies;
    private RadioGroup pesticidesGroup;
    private TextInputEditText howDate, howHour;
    private TextView howAge, howPesticide, titleHighgroves;
    private SeekBar howHighgroves;
    private ImageButton editDateButton, editHourButton, addPesticideButton;
    private Button planOperationButton, cancelButton;

    private String date, hour, pesticides;
    private int highgroves, age=90, typeOfPesticides=0;


    @RequiresApi(api = Build.VERSION_CODES.S)
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
        pesticidesGroup=view.findViewById(R.id.pesticides_group);
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

    @SuppressLint("SetTextI18n")
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

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
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
                    ShowToast toast = new ShowToast();
                    toast.showErrorToast(context, "UWAGA!\n" + "  Przerwałeś proces planowania zabiegu!", R.drawable.icon_information);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OperationsFragment()).commit();
                }break;
            }
        };
        editDateButton.setOnClickListener(listener);
        editHourButton.setOnClickListener(listener);
        addPesticideButton.setOnClickListener(listener);
        planOperationButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        howHighgroves.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
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

        pesticidesGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.insecticidies:
                    {
                        typeOfPesticides=0;
                        howAge.setText("Wiek papryki: " + age + " dni");
                    }break;
                    case R.id.fungicidies:
                    {
                        typeOfPesticides=1;
                        howAge.setText("Wiek papryki: " + age + " dni");
                    }break;
                    case R.id.herbicidies:
                    {
                        typeOfPesticides=2;
                        howAge.setText("Wiek papryki: -");
                    }break;
                }
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

        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
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
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date=generateStringDate(dayOfMonth,month,year);
        });
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

        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
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
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        clock.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            hour=ToolClass.generateStringTime(hourOfDay,minute);
        });
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
        editor.putString("DATA_OF_OPERATIONS", Objects.requireNonNull(howDate.getText()).toString());
        editor.putString("HOUR_OF_OPERATIONS", Objects.requireNonNull(howHour.getText()).toString());
        editor.putInt("AMOUNT_OF_HIGHGROVES", highgroves);
        editor.putString("PESTICIDES", howPesticide.getText().toString());
        editor.putInt("ID_OF_PESTICIDES", 0);
        editor.putInt("GRACE", 0);
        editor.apply();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfPesticidesFragment()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void validateData() {
        ShowToast showToast = new ShowToast();
        boolean checkDate=false;
        boolean checkHour=false;

        date = Objects.requireNonNull(howDate.getText()).toString();
        hour = Objects.requireNonNull(howHour.getText()).toString();
        pesticides = howPesticide.getText().toString();

        if (date.compareTo("") == 0 || hour.compareTo("") == 0 || pesticides.compareTo("") == 0)
            showToast.showErrorToast(context, "BŁĄD DANYCH!\n" + "  Uzupełnij wszystkie pola!", R.drawable.icon_information);
        else {
            if (ToolClass.checkValidateData(howDate.getText().toString()))
                if(ToolClass.compareDateAndTimeWithCurrentDateAndTime(howDate.getText().toString(), howHour.getText().toString()))
                    if (ToolClass.checkValidateYear(howDate.getText().toString()))
                        checkDate = true;
                    else {
                        showToast.showErrorToast(context, "Podaj poprawny rok!\n" + "  Mamy aktualnie " + getActualYear() + " rok!", R.drawable.icon_calendar);
                        checkDate = false;
                    }
                else
                {
                    showToast.showErrorToast(context, "Błędna data!\n" + "  Podaj przyszłą datę!", R.drawable.icon_calendar);
                    checkDate = false;
                }
            else {
                showToast.showErrorToast(context, "Błędny format daty!\n" + "  [dd.mm.rrrr]", R.drawable.icon_calendar);
                checkDate = false;
            }
            if (ToolClass.checkValidateHour(hour))
                checkHour = true;
            else {
                showToast.showErrorToast(context, "Błędny format godziny!\n" + "  [gg:mm]", R.drawable.icon_clock);
                checkHour = false;
            }
        }
        if(checkDate && checkHour)
            addOperationsToDataBase();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void addOperationsToDataBase() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TEMPORARY_CURRENT_OPERATIONS",Context.MODE_PRIVATE);
        int grace = sharedPreferences.getInt("GRACE", 0);
        int id = sharedPreferences.getInt("ID_OF_PESTICIDES", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,ToolClass.getDay(date));
        calendar.set(Calendar.MONTH,ToolClass.getMonth(date));
        calendar.set(Calendar.YEAR,ToolClass.getYear(date));

        calendar.add(Calendar.DAY_OF_MONTH,grace);
        String dateEndOfGrace=generateStringDate(calendar);

        int fluid;
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

        setNotification(date, hour);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.addOperation(id,date, hour, dateEndOfGrace, age, highgroves, fluid,0);

        ToolClass.clearTemporaryCurrentOperations(context);
        ShowToast toast = new ShowToast();
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie zaplanowałeś zabieg!");

        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OperationsFragment()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void setNotification(String date, String hour) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_MUTABLE);

        Calendar c = ToolClass.generateCalendarDate(date, hour);

        int h = c.get(Calendar.HOUR_OF_DAY);
        h--;
        c.set(Calendar.HOUR_OF_DAY,h);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    }


