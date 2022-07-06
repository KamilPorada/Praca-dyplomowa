package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;

public class TradeStatisticsFragment extends Fragment {

    private Fragment fragment = null;
    private Context context;
    
    private TextView weightOfRedPepper, weightOfYellowPepper, weightOfGreenPepper, weightOfOrangePepper, 
             weightOfBlondPepper, weightOfFirstClassPepper, weightOfSecondClassPepper,
             weightOfCuttingClassPepper, weightFromHighgrove, incomeFromHighgrove,
             totalSumOfWeight, totalSumOfIncome, averagePriceOfPepper;
    private Button btnTradeInCharts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_statistics_of_trade, container, false);
        context=container.getContext();
        findViews(view);
        createListeners();
        loadData();
        return view;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void loadData() {
        weightOfRedPepper.setText(calculateWeightFromColorOfPepper("czerwona") + " kg");
        weightOfYellowPepper.setText(calculateWeightFromColorOfPepper("żółta") + " kg");
        weightOfGreenPepper.setText(calculateWeightFromColorOfPepper("zielona") + " kg");
        weightOfOrangePepper.setText(calculateWeightFromColorOfPepper("pomarańczowa") + " kg");
        weightOfBlondPepper.setText(calculateWeightFromColorOfPepper("blondyna") + " kg");
        weightOfFirstClassPepper.setText(calculateWeightFromClassOfPepper("1") + " kg");
        weightOfSecondClassPepper.setText(calculateWeightFromClassOfPepper("2") + " kg");
        weightOfCuttingClassPepper.setText(calculateWeightFromClassOfPepper("krojona") + " kg");
        weightFromHighgrove.setText(String.format("%.2f", Math.round(calculateWeightFromHighgrove(getHighgroves()) * 100.0) / 100.0) + " kg");
        incomeFromHighgrove.setText(String.format("%.2f", Math.round(calculateIncomeFromHighgrove(getHighgroves()) * 100.0) / 100.0) + " zł");
        totalSumOfWeight.setText(String.format("%.2f", Math.round(calculateWeightFromHighgrove(1) * 100.0) / 100.0) + " kg");
        totalSumOfIncome.setText(String.format("%.2f", Math.round(calculateIncomeFromHighgrove(1) * 100.0) / 100.0) + " zł");
        averagePriceOfPepper.setText(getaveragePriceOfPepper()+ " zł");

    }

    private String getaveragePriceOfPepper() {
      double price = calculateIncomeFromHighgrove(1);
      double weight = calculateWeightFromHighgrove(1);
      String answer = String.format("%.2f", Math.round((price/weight) * 100.0) / 100.0);
      return answer;
    }

    private double calculateIncomeFromHighgrove(double divider) {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromTrade();
        Date calendar = new Date();
        int currentYear=calendar.getYear()+1900;
        int year=0;
        int vat=0;
        double price=0;
        double weight = 0;
        double totalMoney=0;
        String date="";
        String stringVat="";
        String stringTotalMoneyFromTrade="";
        while (k.moveToNext())
        {
            vat=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_VAT));
            price=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
            weight=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
            stringVat="0.0"+vat;
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            year=getYear(date);
            if(currentYear==year)
                totalMoney=totalMoney+(Double.parseDouble(stringVat)+1)*price*weight;
        }
        totalMoney=totalMoney/divider;
        stringTotalMoneyFromTrade=String.format("%.2f", Math.round(totalMoney * 100.0) / 100.0);

        return totalMoney;
    }

    private double calculateWeightFromHighgrove(double divider) {
        DataBaseHelper db= new DataBaseHelper(context);
        Date calendar = new Date();
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromTrade();
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(getYear(date) == calendar.getYear()+1900)
                sum=sum+=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        sum=sum/divider;

        return sum;
    }

    private double getHighgroves() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FARM_DATA",Context.MODE_PRIVATE);
        String highgroves = sharedPreferences.getString("HIGHGROVES","0");
        return Double.parseDouble(highgroves);
    }

    private String calculateWeightFromClassOfPepper(String clas) {
        DataBaseHelper db = new DataBaseHelper(context);
        Date calendar = new Date();
        String date;
        String finalString;
        double sum=0;
        Cursor cursor = db.getWeightFromClass(clas);
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(getYear(date) == calendar.getYear()+1900)
                sum=sum+=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        finalString=String.valueOf(String.format("%.2f", Math.round(sum * 100.0) / 100.0));
        return finalString;
    }

    private String calculateWeightFromColorOfPepper(String color) {
        DataBaseHelper db = new DataBaseHelper(context);
        Date calendar = new Date();
        String date;
        String finalString;
        double sum=0;
        Cursor cursor = db.getWeightFromColor(color);
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(getYear(date) == calendar.getYear()+1900)
                sum=sum+=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        finalString=String.valueOf(String.format("%.2f", Math.round(sum * 100.0) / 100.0));
        return finalString;
    }

    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
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
        weightOfRedPepper=view.findViewById(R.id.weight_of_red_pepper);
        weightOfYellowPepper=view.findViewById(R.id.weight_of_yellow_pepper);
        weightOfGreenPepper=view.findViewById(R.id.weight_of_green_pepper);
        weightOfOrangePepper=view.findViewById(R.id.weight_of_orange_pepper);
        weightOfBlondPepper=view.findViewById(R.id.weight_of_blond_pepper);
        weightOfFirstClassPepper=view.findViewById(R.id.weight_of_first_class_pepper);
        weightOfSecondClassPepper=view.findViewById(R.id.weight_of_second_class_pepper);
        weightOfCuttingClassPepper=view.findViewById(R.id.weight_of_cutting_class_pepper);
        weightFromHighgrove=view.findViewById(R.id.weight_from_highgrove);
        incomeFromHighgrove=view.findViewById(R.id.income_from_highgrove);
        totalSumOfIncome=view.findViewById(R.id.total_sum_of_income);
        totalSumOfWeight=view.findViewById(R.id.total_sum_of_weight);
        averagePriceOfPepper=view.findViewById(R.id.average_price_of_pepper);
        btnTradeInCharts=view.findViewById(R.id.btn_trade_in_charts);
    }

    private void createListeners() {
        btnTradeInCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ColorOfPepperChartFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

}


