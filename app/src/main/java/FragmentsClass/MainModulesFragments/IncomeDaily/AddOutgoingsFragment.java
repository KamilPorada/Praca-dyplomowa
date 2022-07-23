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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class AddOutgoingsFragment extends Fragment {

    private Context context;
    private Spinner howCategory;
    private ImageView image;
    private TextView title;
    private TextInputEditText howDescribe, howPrice, howDate;
    private Button cancelButton, acceptButton;
    private ImageView editDateButton;
    private TextView textViewRow;

    private final int[] images =
            {
              R.drawable.image_highgrove, R.drawable.image_foil, R.drawable.image_water, R.drawable.image_pegs,
              R.drawable.image_seeds, R.drawable.image_plant, R.drawable.image_pesticides, R.drawable.image_fertilizer,
              R.drawable.image_machine, R.drawable.image_tools, R.drawable.icon_question
            };
    private final String[] categories =
            {
                    "Konstrukcje tuneli", "Folie ogrodnicze", "Hydraulika w tunelach", "Paliki do tuneli",
                    "Nasiona papryki", "Sadzonki papryki", "Pestycydy", "Nawozy", "Maszyny rolnicze",
                    "Narzędzia ogrodnicze", "Inne"
            };
    private String currentCategory;
    private int idOfCategory=0;
    private int OutgoingOpenMode, id;
    private String calendarDate = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_add_outgoings, container, false);
        assert container != null;
        context=container.getContext();
        howCategory=view.findViewById(R.id.how_category);
        findViews(view);
        startSettings();
        loadData();
        createListeners();
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
        howCategory=view.findViewById(R.id.how_category);
        image=view.findViewById(R.id.image);
        title=view.findViewById(R.id.title);
        howDescribe=view.findViewById(R.id.how_describe);
        howPrice=view.findViewById(R.id.how_price);
        howDate=view.findViewById(R.id.how_date);
        cancelButton=view.findViewById(R.id.cancel_button);
        acceptButton=view.findViewById(R.id.accept_button);
        editDateButton=view.findViewById(R.id.edit_date_button);

        SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("POSITION_OF_OUTGOING_RV", 0);
        OutgoingOpenMode = sharedPreferences.getInt("OUTGOING_OPEN_MODE", 0);

    }

    private void startSettings() {
        ArrayList<OutgoingsSpinnerItem> outGoingsSpinnerItems = new ArrayList<>();
        for(int i=0; i<images.length;i++)
            outGoingsSpinnerItems.add(new OutgoingsSpinnerItem(categories[i], images[i]));

        OutgoingsSpinnerAdapter adapter = new OutgoingsSpinnerAdapter(context, outGoingsSpinnerItems);
        howCategory.setAdapter(adapter);
        currentCategory=categories[0];
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        if(OutgoingOpenMode==0)
        {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            title.setText("Edycja wydatku");
            Cursor k = dataBaseHelper.getSpecifyOutgoingValues(id);
            k.moveToFirst();
            String date = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            String describe = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING));
            double price = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
            int idOfCategory = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY));
            howDate.setText(date);
            howDescribe.setText(describe);
            howPrice.setText(String.valueOf(price));
            howCategory.setSelection(idOfCategory);
            image.setImageResource(images[idOfCategory]);
        }
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id = v.getId();
            switch (id)
            {
                case R.id.accept_button:
                {
                    validateData();
                }break;
                case R.id.cancel_button:
                {
                    ShowToast toast = new ShowToast();
                    if(OutgoingOpenMode==0)
                        toast.showErrorToast(context, "UWAGA!\n" + "  Przerwałeś proces edycji wydatku!", R.drawable.icon_edit);
                    else if(OutgoingOpenMode==1)
                        toast.showErrorToast(context, "UWAGA!\n" + "  Przerwałeś proces dodawania wydatku!", R.drawable.icon_plus);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OutgoingsFragment()).commit();
                }break;
            }
        };
        acceptButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        howCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewRow= view.findViewById(R.id.text_view_row);
                idOfCategory=position;
                image.setImageResource(images[position]);
                currentCategory=textViewRow.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editDateButton.setOnClickListener(v -> openEditDataDialog());
    }

    private void validateData() {
        ShowToast toast = new ShowToast();
        if(Objects.requireNonNull(howDate.getText()).toString().compareTo("")==0 || Objects.requireNonNull(howPrice.getText()).toString().compareTo("")==0 ||
                Objects.requireNonNull(howDescribe.getText()).toString().compareTo("")==0)
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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        ShowToast toast = new ShowToast();
        switch (OutgoingOpenMode)
        {
            case 0:
            {
                dataBaseHelper.updateOutgoingItems(id,currentCategory, idOfCategory, Objects.requireNonNull(howDescribe.getText()).toString(),Double.parseDouble(Objects.requireNonNull(howPrice.getText()).toString()),
                        Objects.requireNonNull(howDate.getText()).toString());
                toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie edytowałeś wydatek!");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OutgoingsFragment()).commit();
            }break;
            case 1:
            {
               dataBaseHelper.addOutgoing(currentCategory, idOfCategory, Objects.requireNonNull(howDescribe.getText()).toString(),Double.parseDouble(Objects.requireNonNull(howPrice.getText()).toString()),
                                           Objects.requireNonNull(howDate.getText()).toString());
                toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie dodałeś nowy wydatek!");
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OutgoingsFragment()).commit();
            }break;
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
}


