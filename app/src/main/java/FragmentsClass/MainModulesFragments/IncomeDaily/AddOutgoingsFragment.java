package FragmentsClass.MainModulesFragments.IncomeDaily;

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
import java.util.Date;

import DataBase.DataBaseHelper;
import OthersClass.InformationDialog;
import OthersClass.ShowAttention;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsSpinnerItem;

public class AddOutgoingsFragment extends Fragment {

    //------------------------PRIMARY VIEWS--------------------------//
    private Context context;
    private Spinner howCategory;
    private ArrayList<OutgoingsSpinnerItem> outGoingsSpinnerItems;
    private OutgoingsSpinnerAdapter adapter;

    //----------------------ADD_OUTGOINGS VIEWS----------------------//
    private ImageView icon;
    private EditText howDescribe, howPrice, howDate;
    private Button cancelButton, acceptButton;

    private TextView textViewRow;

    //------------------------EXTRA VARIABLES------------------------//
    private int[] images =
            {
              R.drawable.image_highgrove, R.drawable.image_foil, R.drawable.image_water, R.drawable.image_pegs,
              R.drawable.image_seeds, R.drawable.image_plant, R.drawable.image_pesticides, R.drawable.image_fertilizer,
              R.drawable.image_machine, R.drawable.image_tools, R.drawable.icon_question
            };
    private String currentCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_add_outgoings, container, false);
        context=container.getContext();
        findViews(view);
        startSettings();
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
        switch (id)
        {
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_income_daily));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howCategory=view.findViewById(R.id.how_category);
        icon=view.findViewById(R.id.icon);
        howDescribe=view.findViewById(R.id.how_describe);
        howPrice=view.findViewById(R.id.how_price);
        howDate=view.findViewById(R.id.how_date);
        cancelButton=view.findViewById(R.id.cancel_button);
        acceptButton=view.findViewById(R.id.accept_button);
    }

    private void startSettings() {
        outGoingsSpinnerItems = new ArrayList<>();
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Konstrukcje tuneli",R.drawable.image_highgrove));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Folie ogrodnicze",R.drawable.image_foil));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Hydraulika w tunelach",R.drawable.image_water));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Paliki do tuneli",R.drawable.image_pegs));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Nasiona papryki",R.drawable.image_seeds));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Sadzonki papryki",R.drawable.image_plant));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Pestycydy",R.drawable.image_pesticides));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Nawozy",R.drawable.image_fertilizer));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Maszyny rolnicze",R.drawable.image_machine));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Narzędzia ogrodnicze",R.drawable.image_tools));
        outGoingsSpinnerItems.add(new OutgoingsSpinnerItem("Inne",R.drawable.icon_question));


        adapter=new OutgoingsSpinnerAdapter(context, outGoingsSpinnerItems);
        howCategory.setAdapter(adapter);
        currentCategory="Konstrukcje tuneli";
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id)
                {
                    case R.id.accept_button:
                    {
                        validateData();
                    }break;
                    case R.id.cancel_button:
                    {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OutgoingsFragment()).commit();
                    }break;
                }
            }
        };
        acceptButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        howCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewRow= view.findViewById(R.id.text_view_row);
                icon.setImageResource(images[position]);
                currentCategory=textViewRow.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void validateData() {
        ShowAttention showAttention = new ShowAttention();
        boolean validateDescribe=false;
        boolean validatePrice=false;

        if(howDescribe.getText().toString().compareTo("")==0)
            validateDescribe=false;
        else
            validateDescribe=true;
        if(howPrice.getText().toString().compareTo("")==0)
            validatePrice=false;
        else
            validatePrice=true;

        if ((validateDescribe && validatePrice))
            validateDate();
        else
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Uzupełnij wszystkie dane!");
    }

    private void validateDate() {
        ShowAttention showAttention = new ShowAttention();
        boolean validateDay=false;
        boolean validateMonth=false;
        boolean validateDotts=false;
        String date=howDate.getText().toString();

        if(howDate.length()<10)
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Zły format daty!\n[dd.mm.rrrr]");
        else {

            char[] charDate = date.toCharArray();
            String stringDay = Character.toString(charDate[0]) + Character.toString(charDate[1]);
            String firstDots = Character.toString(charDate[2]);
            String stringMonth = Character.toString(charDate[3]) + Character.toString(charDate[4]);
            String secondDots = Character.toString(charDate[5]);
            String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                    Character.toString(charDate[8]) + Character.toString(charDate[9]);

            int day = Integer.parseInt(stringDay);
            int month = Integer.parseInt(stringMonth);
            int year = Integer.parseInt(stringYear);


            if (day > 0 && day < 32)
                validateDay = true;
            else
                validateDay = false;
            if (month > 0 && month < 13)
                validateMonth = true;
            else
                validateMonth = false;
            if (firstDots.compareTo(".") == 0 && secondDots.compareTo(".") == 0)
                validateDotts = true;
            else
                validateDotts = false;


            if (validateDay && validateMonth && validateDotts) {
                checkValidateYear(year);
            }
            else {
                showAttention.showToast(R.layout.toast_layout, null, getActivity(), context, "Zły format daty!\n[dd.mm.rrrr]");
            }
        }
    }

    private void checkValidateYear(int year) {
        Date date = new Date();
        int actualYear=date.getYear()+1900;
        if(year!=actualYear)
        {
            ShowAttention showAttention = new ShowAttention();
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Podaj poprawny rok!\nMamy aktualnie "+actualYear+" rok!");
        }
        else
            addItemToDataBase();
    }

    private void addItemToDataBase() {
        String describe=howDescribe.getText().toString();
        double price=Double.parseDouble(howPrice.getText().toString());
        String date=howDate.getText().toString();

        Date calendar = new Date();
        String key=String.valueOf(calendar.getDate())+String.valueOf(calendar.getMonth()+1)+String.valueOf(calendar.getYear()+1900)+
                String.valueOf(calendar.getHours()+2)+String.valueOf(calendar.getMinutes())+String.valueOf(calendar.getSeconds());

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.addOutgoing(currentCategory,describe,price,date,key);

        Fragment fragment = new OutgoingsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

}


