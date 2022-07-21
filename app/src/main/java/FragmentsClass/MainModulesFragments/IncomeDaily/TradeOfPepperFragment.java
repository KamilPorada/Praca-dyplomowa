package FragmentsClass.MainModulesFragments.IncomeDaily;

import static HelperClasses.ToolClass.checkValidateData;
import static HelperClasses.ToolClass.checkValidateYear;
import static HelperClasses.ToolClass.getActualYear;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import FragmentsClass.MainModulesFragments.Calculators.CalculatorsFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;


public class TradeOfPepperFragment extends Fragment {

    //----------------------PRIMARY VIEWS----------------------------//
    private Context context;
    private final ArrayList<TradePepperItem> TradePepperList = new ArrayList<>();
    private RecyclerView recyclerView;

    //----------------------ADD ITEM WINDOWS VIEWS----------------------------//
    private ImageView buttonComeBack;
    private TextView title;
    private RadioGroup colorRadioGroup, vatRadioGroup, classRadioGroup;
    private RadioButton redColor, yellowColor, greenColor, orangeColor, blondColor,
                        zeroPercent, sevenPercent,
                        firstClass, secondClass, cuttingClass;
    private EditText howPrice, howWeight, howDate, howPlace;
    private Button cancelButton, acceptButton;

    //----------------------ADD ITEM WINDOWS VIEWS----------------------------//
    private Button canButton, delButton;

    //----------------------EXTRA VARIABLES----------------------------//
    private String color;
    private int vat;
    private String clas;
    private boolean isEditable=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_trade_of_pepper,container,false);
        findViews(view);
        startSettings();
        createListener();
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_trade_of_pepper));
            }break;
            case R.id.add_item:
            {
                openAddItemWindow();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListener() {
        buttonComeBack.setOnClickListener( v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new IncomeDailyFragment()).commit());
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        TradePepperAdapter adapter = new TradePepperAdapter(TradePepperList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TradePepperAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                openEditItemDialog(position);
            }

            @Override
            public void onDeleteClick(int position) {
                openDialogQuestion(position);
            }
        });
    }

    // ------------------------ADD ITEM WINDOW EVENTS---------------------------//

    @SuppressLint("SetTextI18n")
    private void openAddItemWindow() {
        Dialog addItemWindow = new Dialog(context);
        addItemWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        addItemWindow.setContentView(R.layout.dialog_add_trade_of_pepper);
        addItemWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addItemWindow.show();
        findAddItemDialogViews(addItemWindow);
        title.setText("Nowa sprzedaż");
        isEditable=false;
        createAndAddListeners(addItemWindow, 0);
    }

    private void findAddItemDialogViews(Dialog addItemWindow) {
        title=addItemWindow.findViewById(R.id.title);
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

    @SuppressLint("NonConstantResourceId")
    private void createAndAddListeners(Dialog addItemWindow, int position) {
        colorRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
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

        classRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
        });

        View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.accept_button:
                {
                    validateData(position);
                    addItemWindow.dismiss();
                }break;
                case R.id.cancel_button:
                {
                    addItemWindow.dismiss();
                }break;
            }
        };
        acceptButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }

    private void validateData(int position) {
        boolean validateColor, validateVat, validateClass;
        boolean validatePrice;
        boolean validateWeight;
        boolean validateDate;
        boolean validatePlace;
        ShowToast showToast = new ShowToast();

        validateColor= redColor.isChecked() || yellowColor.isChecked() || greenColor.isChecked() ||
                orangeColor.isChecked() || blondColor.isChecked();
        validateVat= zeroPercent.isChecked() || sevenPercent.isChecked();
        validateClass= firstClass.isChecked() || secondClass.isChecked() || cuttingClass.isChecked();
        validatePrice= howPrice.getText().toString().compareTo("") != 0;
        validateWeight= howWeight.getText().toString().compareTo("") != 0;
        validateDate= howDate.getText().toString().compareTo("") != 0;
        validatePlace= howPlace.getText().toString().compareTo("") != 0;

        String date=howDate.getText().toString();
        if(validateColor && validateVat &&  validateClass && validatePrice &&
                validateWeight && validateDate && validatePlace) {
            if(checkValidateData(date))
                if(checkValidateYear(date))
                    if(isEditable)
                        updateData(position);
                    else
                        addItemToDataBase();
                else
                    showToast.showErrorToast(context,"Podaj poprawny rok!\nMamy aktualnie "+getActualYear()+" rok!", R.drawable.icon_information);
            else
                showToast.showErrorToast(context, "Zły format daty!\n[dd.mm.rrrr]", R.drawable.icon_information);
        }
        else
            showToast.showErrorToast(context,"Uzupełnij wszystkie pola!", R.drawable.icon_information);
    }


    @SuppressLint("DefaultLocale")
    private void addItemToDataBase() {
        double price;
        double weight;
        double totalSum;
        String date;
        String place;
        String key;
        int image;

        price=Double.parseDouble(howPrice.getText().toString());
        weight=Double.parseDouble(howWeight.getText().toString());
        date=howDate.getText().toString();
        place=howPlace.getText().toString();
        totalSum=calculateTotalSum(price,vat,weight);
        image=ToolClass.getDrawable(color);

        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.get(Calendar.YEAR);

        key= calendar.get(Calendar.DAY_OF_MONTH) +String.valueOf(calendar.get(Calendar.MONTH)+1)+ calendar.get(Calendar.YEAR) +
                calendar.get(Calendar.HOUR-1) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.addTradeOfPepperItem(color,vat,clas,price,weight,totalSum,date,place,image,key);

        Fragment fragment = new TradeOfPepperFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        loadData();
    }

    private double calculateTotalSum(double price, double vat, double weight) {
        if (vat==0)
            return price*weight;
        else
            return price*((vat/100)+1)*weight;
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getTradeOfPepperItems();
        while(k.moveToNext())
        {
            String clas=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER));
            double price=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
            double weight=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
            double sum=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM));
            String date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            String place=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE));
            int image=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_IMAGE));
            String key=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATA_PASWORD));
            @SuppressLint("DefaultLocale") String totalSum=String.format("%.2f", Math.round(sum * 100.0) / 100.0);

            if(getActualYear()==ToolClass.getYear(date))
                TradePepperList.add(new TradePepperItem(image,date,clas,price,weight,totalSum, place,key));
        }
    }

    // ------------------------EDIT ITEM WINDOW EVENTS---------------------------//

    @SuppressLint("SetTextI18n")
    private void openEditItemDialog(int position) {
        Dialog editItemWindow = new Dialog(context);
        editItemWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        editItemWindow.setContentView(R.layout.dialog_add_trade_of_pepper);
        editItemWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editItemWindow.show();
        findAddItemDialogViews(editItemWindow);
        title.setText("Edycja sprzedaży");
        isEditable=true;
        setData(position);
        createAndAddListeners(editItemWindow,position);
    }


    @SuppressLint("NonConstantResourceId")
    private void setData(int position) {
        switch (TradePepperList.get(position).getIPepperImage())
        {
            case R.drawable.image_red_pepper:
            {
                redColor.setChecked(true);
                color="czerwona";
            }break;
            case R.drawable.image_yellow_pepper:
            {
                yellowColor.setChecked(true);
                color="żółta";
            }break;
            case R.drawable.image_green_pepper:
            {
                greenColor.setChecked(true);
                color="zielona";
            }break;
            case R.drawable.image_orange_pepper:
            {
                orangeColor.setChecked(true);
                color="pomarańczowa";
            }break;
            case R.drawable.image_blond_pepper:
            {
                blondColor.setChecked(true);
                color="blondyna";
            }break;
        }
        switch (TradePepperList.get(position).getIPepperClass())
        {
            case "1":
            {
                firstClass.setChecked(true);
                clas="1";
            }break;
            case "2":
            {
                secondClass.setChecked(true);
                clas="2";
            }break;
            case "krojona":
            {
                cuttingClass.setChecked(true);
                clas="krojona";
            }break;
        }
        double sum=TradePepperList.get(position).getIPrice()*1.07*TradePepperList.get(position).getIweight();
        @SuppressLint("DefaultLocale") String stringSum=String.format("%.2f", Math.round(sum * 100.0) / 100.0);
        if(TradePepperList.get(position).getITotalSum().compareTo(stringSum)==0) {
            sevenPercent.setChecked(true);
            vat=7;
        }
        else {
            zeroPercent.setChecked(true);
            vat=0;
        }

        howPlace.setText(TradePepperList.get(position).getIPlace());
        howPrice.setText(String.valueOf(TradePepperList.get(position).getIPrice()));
        howWeight.setText(String.valueOf(TradePepperList.get(position).getIweight()));
        howDate.setText(TradePepperList.get(position).getIDate());
    }

    private void updateData(int position) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            double sum = calculateTotalSum(Double.parseDouble(howPrice.getText().toString()),vat,Double.parseDouble(howWeight.getText().toString()));
            dataBaseHelper.updateTradeOfTradeOfPepperItems(TradePepperList.get(position).getIDataPassword(),
                     color, vat, clas, Double.parseDouble(howPrice.getText().toString()), Double.parseDouble(howWeight.getText().toString()), sum,
                    howDate.getText().toString(),ToolClass.getDrawable(color), howPlace.getText().toString());
            Fragment fragment = new TradeOfPepperFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            loadData();
    }

    // ------------------------DELETE ITEM WINDOW EVENTS---------------------------//

    private void openDialogQuestion(int position) {
        Dialog questionWindow = new Dialog(context);
        questionWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        questionWindow.setContentView(R.layout.dialog_question);
        questionWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        questionWindow.show();
        findQuestionDialogViews(questionWindow);
        createAndAddListener(questionWindow, position);
    }

    private void findQuestionDialogViews(Dialog questionWindow) {
        canButton=questionWindow.findViewById(R.id.cancel_button);
        delButton=questionWindow.findViewById(R.id.delete_button);
    }

    private void createAndAddListener(Dialog questionWindow, int position) {
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
        canButton.setOnClickListener(listener);
        delButton.setOnClickListener(listener);
    }

    private void deleteItem(int position) {
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
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }
}
