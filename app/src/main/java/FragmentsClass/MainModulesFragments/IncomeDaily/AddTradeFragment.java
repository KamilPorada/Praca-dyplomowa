package FragmentsClass.MainModulesFragments.IncomeDaily;

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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class AddTradeFragment extends Fragment {


    private Context context;

    private ImageView image, redColor, yellowColor, greenColor, orangeColor, blondColor;
    private TextView title;
    private RadioGroup vatRadioGroup, classRadioGroup;
    private RadioButton firstClass, secondClass, cuttingClass, zeroPercent, sevenPercent;
    private TextInputEditText howPrice, howWeight, howDate, howPlace;
    private Button cancelButton, acceptButton;
    private ImageButton editDateButton;

    private int color, clas=1, vat=0;
    private int tradeOfPepperOpenMode, id;
    private String calendarDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_add_new_trade, container, false);
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

    private void findViews(View view) {
        image=view.findViewById(R.id.image);
        redColor=view.findViewById(R.id.red_color);
        yellowColor=view.findViewById(R.id.yellow_color);
        greenColor=view.findViewById(R.id.green_color);
        orangeColor=view.findViewById(R.id.orange_color);
        blondColor=view.findViewById(R.id.blond_color);
        editDateButton=view.findViewById(R.id.edit_date_button);
        title=view.findViewById(R.id.title);
        vatRadioGroup=view.findViewById(R.id.vat_radio_group);
        classRadioGroup=view.findViewById(R.id.class_radio_group);
        firstClass=view.findViewById(R.id.first_class);
        secondClass=view.findViewById(R.id.second_class);
        cuttingClass=view.findViewById(R.id.cutting_class);
        zeroPercent=view.findViewById(R.id.zero_percent);
        sevenPercent=view.findViewById(R.id.seven_percent);
        howPrice=view.findViewById(R.id.how_price);
        howWeight=view.findViewById(R.id.how_weight);
        howDate=view.findViewById(R.id.how_date);
        howPlace=view.findViewById(R.id.how_place);
        cancelButton=view.findViewById(R.id.cancel_button);
        acceptButton=view.findViewById(R.id.accept_button);

        SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("POSITION_OF_TRADE_RV", 0);
        tradeOfPepperOpenMode = sharedPreferences.getInt("TRADE_OF_PEPPER_OPEN_MODE", 1);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        if(tradeOfPepperOpenMode==0) {
            title.setText("Edycja sprzedaży");
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            Cursor k = dataBaseHelper.getSpecifyTradeOfPepperValues(id);
            int eColor = 0, eClas = 1, eVat = 0;
            double eWeight = 0, ePrice = 0;
            String eDate = "", ePlace = "";
            while (k.moveToNext()) {
                eColor = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER));
                eClas = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER));
                eVat = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_VAT));
                eWeight = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
                ePrice = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
                eDate = k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
                ePlace = k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE));
            }

            image.setImageResource(ToolClass.getPepperDrawable(eColor));
            color=eColor;
            clas=eClas;
            vat=eVat;
            if (eClas == 1)
                firstClass.setChecked(true);
            else if (eClas == 2)
                secondClass.setChecked(true);
            else if (eClas == 3)
                cuttingClass.setChecked(true);

            if (eVat == 0)
                zeroPercent.setChecked(true);
            else if (eVat == 7)
                sevenPercent.setChecked(true);

            howWeight.setText(String.valueOf(eWeight));
            howPrice.setText(String.valueOf(ePrice));
            howDate.setText(String.valueOf(eDate));
            howPlace.setText(String.valueOf(ePlace));
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void createListeners() {
        View.OnClickListener listener = v -> {
            int id =v.getId();
            switch (id)
            {
                case R.id.cancel_button:
                {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TradeOfPepperFragment()).commit();
                }break;
                case R.id.accept_button:
                {
                    validateData();
                }break;
            }
        };
        cancelButton.setOnClickListener(listener);
        acceptButton.setOnClickListener(listener);

        editDateButton.setOnClickListener(v -> {
            openEditDataDialog();
        });

        View.OnClickListener colorListener = v -> {
            int id = v.getId();
            switch (id)
            {
                case R.id.red_color:
                {
                    color=0;
                }break;
                case R.id.yellow_color:
                {
                    color=1;
                }break;
                case R.id.green_color:
                {
                    color=2;
                }break;
                case R.id.orange_color:
                {
                    color=3;
                }break;
                case R.id.blond_color:
                {
                    color=4;
                }break;
            }
            image.setImageResource(ToolClass.getPepperDrawable(color));
        };
        redColor.setOnClickListener(colorListener);
        yellowColor.setOnClickListener(colorListener);
        greenColor.setOnClickListener(colorListener);
        orangeColor.setOnClickListener(colorListener);
        blondColor.setOnClickListener(colorListener);

        classRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.first_class:
                {
                    clas=1;
                }break;
                case R.id.second_class:
                {
                    clas=2;
                }break;
                case R.id.cutting_class:
                {
                    clas=3;
                }
            }
        });

        vatRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.zero_percent:
                {
                    vat=0;
                }break;
                case R.id.seven_percent:
                {
                    vat=7;
                }break;
            }
        });
    }

    private void validateData() {
        ShowToast toast = new ShowToast();

        if(Objects.requireNonNull(howWeight.getText()).toString().compareTo("")==0 || Objects.requireNonNull(howPrice.getText()).toString().compareTo("")==0 ||
           Objects.requireNonNull(howDate.getText()).toString().compareTo("")==0   || Objects.requireNonNull(howPlace.getText()).toString().compareTo("")==0 )
            toast.showErrorToast(context, "BŁĄD DANYCH!\n"+"  Uzupełnij wszystkie pola!", R.drawable.icon_information);
        else {
            if(ToolClass.checkValidateData(howDate.getText().toString()))
                if(ToolClass.checkValidateYear(howDate.getText().toString()))
                    addOrEditItem();
                else
                    toast.showErrorToast(context, "Podaj poprawny rok!\n" + "  Mamy aktualnie " + ToolClass.getActualYear() + " rok!", R.drawable.icon_calendar);
            else
                toast.showErrorToast(context, "Błędny format daty!\n" + "  [dd.mm.rrrr]", R.drawable.icon_calendar);
        }
    }

    private void addOrEditItem() {
        double weight = Double.parseDouble(Objects.requireNonNull(howWeight.getText()).toString());
        double price = Double.parseDouble(Objects.requireNonNull(howPrice.getText()).toString());
        double totalSum = calculateTotalSum(price,vat,weight);
        String date = Objects.requireNonNull(howDate.getText()).toString();
        String place = Objects.requireNonNull(howPlace.getText()).toString();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        switch (tradeOfPepperOpenMode)
        {
            case 0:
            {
                dataBaseHelper.updateTradeOfTradeOfPepperItems(id, color,vat,clas,price,weight,totalSum,date,place);
            }break;
            case 1:
            {
                dataBaseHelper.addTradeOfPepperItem(color,vat,clas,price,weight,totalSum,date,place);
            }break;
        }
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TradeOfPepperFragment()).commit();
    }

    private double calculateTotalSum(double price, double vat, double weight) {
        if (vat==0)
            return price*weight;
        else
            return price*((vat/100)+1)*weight;
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

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendarDate=generateStringDate(dayOfMonth,month,year);
        });
    }

}


