package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;

public class BalanceFragment extends Fragment {

    private Fragment fragment = null;
    private Context context;

    PieChart chart;
    private Button outgoingsButton, tradeButton;

    float moneyFromTrades, moneyFromOutgoings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_balance, container, false);
        context=container.getContext();
        findViews(view);
        createListeners();
        calculateMoney();
        createChart();
        return view;
    }

    private void createChart() {
        ArrayList<PieEntry> colors = new ArrayList<>();
        colors.add(new PieEntry((moneyFromTrades),"Dochody"));
        colors.add(new PieEntry((moneyFromOutgoings),"Wydatki"));

        final int [] colorsOfPie = {
                getResources().getColor(R.color.mainColor), getResources().getColor(R.color.red)
        };


        PieDataSet pieDataSet = new PieDataSet(colors,"");
        pieDataSet.setColors(colorsOfPie);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(20f);


        Legend l = chart.getLegend();
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        chart.setDrawHoleEnabled(true);


        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf("±"+Math.round(value/1000) + " tyś. zł");
            }
        });


        chart.setData(pieData);
        chart.setDrawHoleEnabled(true);
        chart.invalidate();
        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextSize(15f);
        chart.setCenterTextSize(20f);
        chart.setHoleColor(getResources().getColor(R.color.backgroundColor));
        chart.getLegend().setTextColor(getResources().getColor(R.color.blackToWhite));
        chart.setCenterTextColor(getResources().getColor(R.color.blackToWhite));
        chart.animateY(1000 , Easing.EaseInCirc);
    }

    private void calculateMoney() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromTrade();
        Date calendar = new Date();
        int currentYear=calendar.getYear()+1900;
        int year=0;
        int vat=0;
        double price=0;
        double weight = 0;
        String date="";
        String stringVat="";
        while (k.moveToNext())
        {
            vat=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_VAT));
            price=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
            weight=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
            stringVat="0.0"+vat;
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            year=getYear(date);
            if(currentYear==year)
                moneyFromTrades= (float) (moneyFromTrades+(Double.parseDouble(stringVat)+1)*price*weight);
        }

        k=dbHelper.getMoneyFromOutgoings();
        while (k.moveToNext())
        {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            year=getYear(date);
            if(currentYear==year)
                moneyFromOutgoings= (float) (moneyFromOutgoings+k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING)));
        }

    }

    private void setMoney() {
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
        stringTotalMoneyFromTrade=String.format("%.2f", Math.round(totalMoney * 100.0) / 100.0);

        double moneyFromOutgoing=0;
        k=dbHelper.getMoneyFromOutgoings();
        while (k.moveToNext())
        {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            year=getYear(date);
            if(currentYear==year)
                moneyFromOutgoing=moneyFromOutgoing+k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
        }

//        howTradeMoney.setText(stringTotalMoneyFromTrade + " zł");
//        howOutgoingsMoney.setText(String.valueOf(moneyFromOutgoing) + " zł");
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
        outgoingsButton=view.findViewById(R.id.btn_outgoings);
        tradeButton=view.findViewById(R.id.btn_trade);
        chart=view.findViewById(R.id.chart);
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_trade:
                    {
                        fragment = new TradeStatisticsFragment();
                    }break;
                    case R.id.btn_outgoings:
                    {
                        fragment = new OutgoingsStatisticsFragment();
                    }break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        };
        tradeButton.setOnClickListener(listener);
        outgoingsButton.setOnClickListener(listener);
    }

}


