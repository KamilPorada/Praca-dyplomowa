package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DataBase.DataBaseHelper;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class AddOutgoingsFragment extends Fragment {

    //------------------------PRIMARY VIEWS--------------------------//
    private Context context;
    private Spinner howCategory;

    //----------------------ADD_OUTGOINGS VIEWS----------------------//
    private ImageView icon;
    private EditText howDescribe, howPrice, howDate;
    private Button cancelButton, acceptButton;

    private TextView textViewRow;

    //------------------------EXTRA VARIABLES------------------------//
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
    private int currentImage;
    private boolean isEditable=false;
    private String password;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_add_outgoings, container, false);
        assert container != null;
        context=container.getContext();
        howCategory=view.findViewById(R.id.how_category);
//        findViews(view);
        startSettings();
//        getData();
//        createListeners();
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

//    private void findViews(View view) {
//        howCategory=view.findViewById(R.id.how_category);
//        icon=view.findViewById(R.id.icon);
//        howDescribe=view.findViewById(R.id.how_describe);
//        howPrice=view.findViewById(R.id.how_price);
//        howDate=view.findViewById(R.id.how_date);
//        cancelButton=view.findViewById(R.id.cancel_button);
//        acceptButton=view.findViewById(R.id.accept_button);
//    }
//
    private void startSettings() {
        ArrayList<OutgoingsSpinnerItem> outGoingsSpinnerItems = new ArrayList<>();
        for(int i=0; i<images.length;i++)
            outGoingsSpinnerItems.add(new OutgoingsSpinnerItem(categories[i], images[i]));


        OutgoingsSpinnerAdapter adapter = new OutgoingsSpinnerAdapter(context, outGoingsSpinnerItems);
        howCategory.setAdapter(adapter);
        currentCategory=categories[0];
    }
//
//    private void getData() {
//        Bundle bundle = this.getArguments();
//        String FLAG = "flag";
//        assert bundle != null;
//        isEditable = bundle.getBoolean(FLAG);
//        System.out.println(isEditable);
//        if(isEditable)
//            setData();
//    }
//
//    private void setData() {
//        Bundle bundle = this.getArguments();
//        assert bundle != null;
//        String PRICE = "price";
//        String DATE = "date";
//        String DESCRIBE = "describe";
//        String CATEGORY = "category";
//        String PASSSWORD_KEY = "passwordKey";
//        String category = bundle.getString(CATEGORY);
//        howPrice.setText(String.valueOf(bundle.getDouble(PRICE)));
//        howDate.setText(bundle.getString(DATE));
//        howDescribe.setText(bundle.getString(DESCRIBE));
//        password=bundle.getString(PASSSWORD_KEY);
//        int index=0;
//        for(int i=0;i<categories.length;i++)
//            if(category.compareTo(categories[i])==0)
//                index=i;
//        howCategory.setSelection(index);
//    }
//
//    private void createListeners() {
//        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
//            int id = v.getId();
//            switch (id)
//            {
//                case R.id.accept_button:
//                {
//                    validateData();
//                }break;
//                case R.id.cancel_button:
//                {
//                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OutgoingsFragment()).commit();
//                }break;
//            }
//        };
//        acceptButton.setOnClickListener(listener);
//        cancelButton.setOnClickListener(listener);
//
//        howCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                textViewRow= view.findViewById(R.id.text_view_row);
//                icon.setImageResource(images[position]);
//                currentImage=images[position];
//                currentCategory=textViewRow.getText().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    private void validateData() {
//        ShowToast showToast = new ShowToast();
//        boolean validateDescribe, validatePrice;
//        String date=howDate.getText().toString();
//        validateDescribe= howDescribe.getText().toString().compareTo("") != 0;
//        validatePrice= howPrice.getText().toString().compareTo("") != 0;
//        if ((validateDescribe && validatePrice))
//            if(ToolClass.checkValidateData(date))
//                if(ToolClass.checkValidateYear(date))
//                    if(isEditable)
//                       updateItem();
//                    else
//                        addItemToDataBase();
//                else
//                    showToast.showErrorToast(context,"Podaj poprawny rok!\nMamy aktualnie "+ToolClass.getActualYear()+" rok!", R.drawable.icon_information);
//            else
//                showToast.showErrorToast(context, "Zły format daty!\n[dd.mm.rrrr]", R.drawable.icon_information);
//        else
//            showToast.showErrorToast(context,"Uzupełnij wszystkie dane!", R.drawable.icon_information);
//    }
//
//    private void addItemToDataBase() {
//        String describe=howDescribe.getText().toString();
//        double price=Double.parseDouble(howPrice.getText().toString());
//        String date=howDate.getText().toString();
//
//        Date d = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(d);
//        calendar.get(Calendar.YEAR);
//
//        String key= calendar.get(Calendar.DAY_OF_MONTH) +String.valueOf(calendar.get(Calendar.MONTH)+1)+ calendar.get(Calendar.YEAR) +
//                calendar.get(Calendar.HOUR-1) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND);
//
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
//        dataBaseHelper.addOutgoing(currentCategory,describe,price,date,currentImage,key);
//
//        Fragment fragment = new OutgoingsFragment();
//        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
//    }
//
//    private void updateItem() {
//        String describe=howDescribe.getText().toString();
//        double price=Double.parseDouble(howPrice.getText().toString());
//        String date=howDate.getText().toString();
//
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
//        dataBaseHelper.updateOutgoingItems(password,currentCategory,describe,price,currentImage,date);
//
//        Fragment fragment = new OutgoingsFragment();
//        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
//    }

}


