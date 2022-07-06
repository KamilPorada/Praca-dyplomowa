package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;
import OthersClass.ShowAttention;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperItem;


public class TradeOfPepperFragment extends Fragment {

    //----------------------PRIMARY VIEWS----------------------------//
    private Context context;
    private  ArrayList<TradePepperItem> TradePepperList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TradePepperAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //----------------------ADD ITEM WINDOWS VIEWS----------------------------//
    private RadioGroup colorRadioGroup, vatRadioGroup, classRadioGroup;
    private RadioButton redColor, yellowColor, greenColor, orangeColor, blondColor,
                        zeroPercent, sevenPercent,
                        firstClass, secondClass, cuttingClass;
    private EditText howPrice, howWeight, howDate, howPlace;
    private Button cancelButton, acceptButton;

    //----------------------EDIT ITEM WINDOWS VIEWS----------------------------//
    private RadioGroup colorRadioGroupp, vatRadioGroupp, classRadioGroupp;
    private RadioButton redColorr, yellowColorr, greenColorr, orangeColorr, blondColorr,
            zeroPercentt, sevenPercentt,
            firstClasss, secondClasss, cuttingClasss;
    private EditText howPricee, howWeightt, howDatee, howPlacee;
    private Button cancelButtonn, updateButtonn;

    //----------------------EXTRA VARIABLES----------------------------//
    private String color;
    private int vat;
    private String clas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_trade_of_pepper,container,false);
        findViews(view);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tollbar_menu_add_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
    }
    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        adapter = new TradePepperAdapter(TradePepperList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TradePepperAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                openEditItemDialog(position);
            }

            @Override
            public void onDeleteClick(int position) {
                DataBaseHelper db = new DataBaseHelper(context);
                Cursor cursor = db.getItemID(DataBaseNames.TradeOfPepperItem.TABLE_NAME,DataBaseNames.TradeOfPepperItem._ID,
                        DataBaseNames.TradeOfPepperItem.COLUMN_DATA_PASWORD,TradePepperList.get(position).getIDataPassword());
                int id=0;
                while (cursor.moveToNext())
                {
                    id=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem._ID));
                }
                db.deleteItem(DataBaseNames.TradeOfPepperItem.TABLE_NAME,id);
                Fragment fragment = new TradeOfPepperFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                loadData();
            }
        });
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getTradeOfPepperItems();
        int im = 0;
        while(k.moveToNext())
        {
            String color=k.getString(1);
            int vat=k.getInt(2);
            String clas=k.getString(3);
            double price=k.getDouble(4);
            double weight=k.getDouble(5);
            String date=k.getString(6);
            String place=k.getString(7);
            String key=k.getString(8);

            switch (color)
            {
                case "czerwona":
                {
                    im=R.drawable.image_red_pepper;
                }break;
                case "żółta":
                {
                    im=R.drawable.image_yellow_pepper;
                }break;
                case "zielona":
                {
                    im=R.drawable.image_green_pepper;
                }break;
                case "pomarańczowa":
                {
                    im=R.drawable.image_orange_pepper;
                }break;
                case "blondyna":
                {
                    im=R.drawable.image_blond_pepper;
                }break;
            }
            double totalSum=calculateTotalSum(price,vat,weight);
            String stringTotalSum=String.format("%.2f", Math.round(totalSum * 100.0) / 100.0);

            int year = getYear(date);
            Date calendar = new Date();
            if((calendar.getYear()+1900==year))
                TradePepperList.add(new TradePepperItem(im,date,clas,price,weight,stringTotalSum, place,key));
        }
    }

    private double calculateTotalSum(double price, double vat, double weight) {
        if (vat==0)
        {
            return price*weight;
        }
        else
        {
            return price*((vat/100)+1)*weight;
        }
    }

    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_about_application));
            }break;
            case R.id.add_item:
            {
                openAddItemWindow();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ------------------------ADD ITEM WINDOW EVENTS---------------------------//

    private void openAddItemWindow() {
        Dialog addItemWindow = new Dialog(context);
        addItemWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        addItemWindow.setContentView(R.layout.dialog_add_trade_of_pepper);
        addItemWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addItemWindow.show();
        findAddItemDialogViews(addItemWindow);
        createAndAddListeners(addItemWindow);
    }

    private void findAddItemDialogViews(Dialog addItemWindow) {
        colorRadioGroup=addItemWindow.findViewById(R.id.color_radio_group);
        vatRadioGroup=addItemWindow.findViewById(R.id.vat_radio_group);
        classRadioGroup=addItemWindow.findViewById(R.id.class_radio_group);
        howPrice=addItemWindow.findViewById(R.id.how_price);
        howWeight=addItemWindow.findViewById(R.id.how_weight);
        howDate=addItemWindow.findViewById(R.id.how_date);
        howPlace=addItemWindow.findViewById(R.id.how_place);
        acceptButton=addItemWindow.findViewById(R.id.accept_button);
        cancelButton=addItemWindow.findViewById(R.id.cancel_button);
        redColor=addItemWindow.findViewById(R.id.red_color);
        yellowColor=addItemWindow.findViewById(R.id.yellow_color);
        greenColor=addItemWindow.findViewById(R.id.green_color);
        orangeColor=addItemWindow.findViewById(R.id.orange_color);
        blondColor=addItemWindow.findViewById(R.id.blond_color);
        zeroPercent=addItemWindow.findViewById(R.id.zero_percent);
        sevenPercent=addItemWindow.findViewById(R.id.seven_percent);
        firstClass=addItemWindow.findViewById(R.id.first_class);
        secondClass=addItemWindow.findViewById(R.id.second_class);
        cuttingClass=addItemWindow.findViewById(R.id.cutting_class);
    }

    private void createAndAddListeners(Dialog addItemWindow) {
        colorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.red_color:
                    {
                        color="czerwona";
                    }break;
                    case R.id.yellow_color:
                    {
                        color="żółta";
                    }break;
                    case R.id.green_color:
                    {
                        color="zielona";
                    }break;
                    case R.id.orange_color:
                    {
                        color="pomarańczowa";
                    }break;
                    case R.id.blond_color:
                    {
                        color="blondyna";
                    }break;
                }
            }
        });

        vatRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
            }
        });

        classRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.first_class:
                    {
                        clas="1";
                    }break;
                    case R.id.second_class:
                    {
                        clas="2";
                    }break;
                    case R.id.cutting_class:
                    {
                        clas="krojona";
                    }break;
                }
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.accept_button:
                    {
                        validateData();
                        addItemWindow.dismiss();
                    }break;
                    case R.id.cancel_button:
                    {
                        addItemWindow.dismiss();
                    }break;
                }
            }
        };
        acceptButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }

    private void validateData() {
        boolean validateColor=false;
        boolean validateVat=false;
        boolean validateClass=false;
        boolean validatePrice=false;
        boolean validateWeight=false;
        boolean validateDate=false;
        boolean validatePlace=false;

        if(!redColor.isChecked() && !yellowColor.isChecked() && !greenColor.isChecked() &&
                !orangeColor.isChecked() && !blondColor.isChecked())
            validateColor=false;
        else
            validateColor=true;
        if(!zeroPercent.isChecked() && !sevenPercent.isChecked())
            validateVat=false;
        else
            validateVat=true;
        if(!firstClass.isChecked() && !secondClass.isChecked() && !cuttingClass.isChecked())
            validateClass=false;
        else
            validateClass=true;
        if(String.valueOf(howPrice.getText().toString()).compareTo("")==0)
            validatePrice=false;
        else
            validatePrice=true;
        if(String.valueOf(howWeight.getText().toString()).compareTo("")==0)
            validateWeight=false;
        else
            validateWeight=true;
        if(String.valueOf(howDate.getText().toString()).compareTo("")==0 )
            validateDate=false;
        else
            validateDate=true;
        if(String.valueOf(howPlace.getText().toString()).compareTo("")==0)
            validatePlace=false;
        else
            validatePlace=true;

        String date=howDate.getText().toString();
        if(validateColor && validateVat &&  validateClass && validatePrice &&
                validateWeight && validateDate && validatePlace) {
            checkValidateData(date);
        }
        else
        {
            ShowAttention showAttention = new ShowAttention();
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Uzupełnij wszystkie pola!");
        }
    }

    private void checkValidateData(String date) {
        ShowAttention showAttention = new ShowAttention();
        boolean validateDay=false;
        boolean validateMonth=false;
        boolean validateDotts=false;


        if(date.length()<10)
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
        double price=0;
        double weight=0;
        String date="";
        String place="";
        String key="";

        price=Double.parseDouble(howPrice.getText().toString());
        weight=Double.parseDouble(howWeight.getText().toString());
        date=howDate.getText().toString();
        place=howPlace.getText().toString();

        Date calendar = new Date();
        key=String.valueOf(calendar.getDate())+String.valueOf(calendar.getMonth()+1)+String.valueOf(calendar.getYear()+1900)+
                String.valueOf(calendar.getHours()+2)+String.valueOf(calendar.getMinutes())+String.valueOf(calendar.getSeconds());

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.addTradeOfPepperItem(color,vat,clas,price,weight,date,place,key);

        Fragment fragment = new TradeOfPepperFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        loadData();
    }

    // ------------------------EDIT ITEM WINDOW EVENTS---------------------------//

    private void openEditItemDialog(int position) {
        Dialog editItemDialog = new Dialog(context);
        editItemDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        editItemDialog.setContentView(R.layout.dialog_upadate_or_remove_trade_of_pepper);
        editItemDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editItemDialog.show();
        findEditItemDialogviews(editItemDialog,position);
        setData(position);
        createAndAddListeners(editItemDialog, position);
    }

    private void findEditItemDialogviews(Dialog editItemDialog, int position) {
        colorRadioGroupp=editItemDialog.findViewById(R.id.color_radio_group);
        vatRadioGroupp=editItemDialog.findViewById(R.id.vat_radio_group);
        classRadioGroupp=editItemDialog.findViewById(R.id.class_radio_group);
        howPricee=editItemDialog.findViewById(R.id.how_price);
        howWeightt=editItemDialog.findViewById(R.id.how_weight);
        howDatee=editItemDialog.findViewById(R.id.how_date);
        howPlacee=editItemDialog.findViewById(R.id.how_place);
        cancelButtonn=editItemDialog.findViewById(R.id.cancel_button);
        updateButtonn=editItemDialog.findViewById(R.id.update_button);
        redColorr=editItemDialog.findViewById(R.id.red_color);
        yellowColorr=editItemDialog.findViewById(R.id.yellow_color);
        greenColorr=editItemDialog.findViewById(R.id.green_color);
        orangeColorr=editItemDialog.findViewById(R.id.orange_color);
        blondColorr=editItemDialog.findViewById(R.id.blond_color);
        zeroPercentt=editItemDialog.findViewById(R.id.zero_percent);
        sevenPercentt=editItemDialog.findViewById(R.id.seven_percent);
        firstClasss=editItemDialog.findViewById(R.id.first_class);
        secondClasss=editItemDialog.findViewById(R.id.second_class);
        cuttingClasss=editItemDialog.findViewById(R.id.cutting_class);
    }

    private void setData(int position) {
        switch (TradePepperList.get(position).getIPepperImage())
        {
            case R.drawable.image_red_pepper:
            {
                redColorr.setChecked(true);
                color="czerwona";
            }break;
            case R.drawable.image_yellow_pepper:
            {
                yellowColorr.setChecked(true);
                color="żółta";
            }break;
            case R.drawable.image_green_pepper:
            {
                greenColorr.setChecked(true);
                color="zielona";
            }break;
            case R.drawable.image_orange_pepper:
            {
                orangeColorr.setChecked(true);
                color="pomarańczowa";
            }break;
            case R.drawable.image_blond_pepper:
            {
                blondColorr.setChecked(true);
                color="blondyna";
            }break;
        }
        switch (TradePepperList.get(position).getIPepperClass())
        {
            case "1":
            {
                firstClasss.setChecked(true);
                clas="1";
            }break;
            case "2":
            {
                secondClasss.setChecked(true);
                clas="2";
            }break;
            case "krojona":
            {
                cuttingClasss.setChecked(true);
                clas="krojona";
            }break;
        }
        double sum=TradePepperList.get(position).getIPrice()*1.07*TradePepperList.get(position).getIweight();
        String stringSum=String.format("%.2f", Math.round(sum * 100.0) / 100.0);
        if(TradePepperList.get(position).getITotalSum().compareTo(stringSum)==0) {
            sevenPercentt.setChecked(true);
            vat=7;
        }
        else {
            zeroPercentt.setChecked(true);
            vat=0;
        }

        howPlacee.setText(TradePepperList.get(position).getIPlace());
        howPricee.setText(String.valueOf(TradePepperList.get(position).getIPrice()));
        howWeightt.setText(String.valueOf(TradePepperList.get(position).getIweight()));
        howDatee.setText(TradePepperList.get(position).getIDate());

    }

    private void createAndAddListeners(Dialog editItemDialog, int position)
    {
        colorRadioGroupp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.red_color:
                    {
                        color="czerwona";
                    }break;
                    case R.id.yellow_color:
                    {
                        color="żółta";
                    }break;
                    case R.id.green_color:
                    {
                        color="zielona";
                    }break;
                    case R.id.orange_color:
                    {
                        color="pomarańczowa";
                    }break;
                    case R.id.blond_color:
                    {
                        color="blondyna";
                    }break;
                }
            }
        });

        vatRadioGroupp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
            }
        });

        classRadioGroupp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.first_class:
                    {
                        clas="1";
                    }break;
                    case R.id.second_class:
                    {
                        clas="2";
                    }break;
                    case R.id.cutting_class:
                    {
                        clas="krojona";
                    }break;
                }
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.cancel_button:
                    {
                        editItemDialog.dismiss();
                    }break;
                    case R.id.update_button:
                    {
                        updateData(position);
                        editItemDialog.dismiss();
                    }break;
                }
            }
        };
        cancelButtonn.setOnClickListener(listener);
        updateButtonn.setOnClickListener(listener);

    }

    private boolean validateUpdateDialogData()
    {
        ShowAttention showAttention = new ShowAttention();
        String date=howDatee.getText().toString();
        boolean validateDay=false;
        boolean validateMonth=false;
        boolean validateDotts=false;

        if(howPricee.getText().toString().compareTo("")==0 || howWeightt.getText().toString().compareTo("")==0 ||
                howDatee.getText().toString().compareTo("")==0 || howPlacee.getText().toString().compareTo("")==0)
        {
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Uzupełnij wszystkie pola!");
            return false;
        }
        else {
            if (date.length() < 10) {
                showAttention.showToast(R.layout.toast_layout, null, getActivity(), context, "Zły format daty!\n[dd.mm.rrrr]");
                return false;
            } else {

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
                    Date calendar = new Date();
                    int actualYear = calendar.getYear() + 1900;
                    if (year != actualYear) {
                        showAttention.showToast(R.layout.toast_layout, null, getActivity(), context, "Podaj poprawny rok!\nMamy aktualnie " + actualYear + " rok!");
                        return false;
                    } else
                        return true;
                } else {
                    showAttention.showToast(R.layout.toast_layout, null, getActivity(), context, "Zły format daty!\n[dd.mm.rrrr]");
                    return false;
                }
            }
        }
    }


    private void updateData(int position) {
        if(validateUpdateDialogData()) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            dataBaseHelper.updateTradeOfTradeOfPepperItems(TradePepperList.get(position).getIDataPassword(),
                    color, vat, clas, Double.parseDouble(howPricee.getText().toString()), Double.parseDouble(howWeightt.getText().toString()),
                    howDatee.getText().toString(), howPlacee.getText().toString());
            Fragment fragment = new TradeOfPepperFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            loadData();
        }
    }

}
