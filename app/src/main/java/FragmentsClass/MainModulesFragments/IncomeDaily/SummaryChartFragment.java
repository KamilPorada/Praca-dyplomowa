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
import android.widget.ImageView;
import android.widget.TextView;

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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;

public class SummaryChartFragment extends Fragment {

    Context context;
    Fragment fragment = null;

    TextView title;
    PieChart chart;
    Button btnTradeInNumbers;
    ImageView btnLeft, btnRight;

    float moneyFromTrades, moneyFromOutgoings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_summary_colors_class_of_pepper_chart_fragment, container, false);
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
        chart.setUsePercentValues(true);
        chart.setDrawHoleEnabled(true);


        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(chart));


        chart.setData(pieData);
        chart.invalidate();
        chart.setUsePercentValues(true);
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


    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_trade_in_numbers:
                    {
                        fragment = new BalanceFragment();
                    }break;
                    case R.id.btn_left:
                    {
                        fragment = new ColorOfPepperChartFragment();
                    }break;
                    case R.id.btn_right:
                    {
                        fragment = new MonthlyWeightFromPepperChartFragment();
                    }break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        };
        btnTradeInNumbers.setOnClickListener(listener);
        btnLeft.setOnClickListener(listener);
        btnRight.setOnClickListener(listener);
    }



    private void findViews(View view) {
        title=view.findViewById(R.id.title);
        chart=view.findViewById(R.id.chart);
        btnTradeInNumbers=view.findViewById(R.id.btn_trade_in_numbers);
        btnLeft=view.findViewById(R.id.btn_left);
        btnRight=view.findViewById(R.id.btn_right);
        title.setText("Wykres przedstawiający procentową ilość zysków i wydatków w bieżącym sezonie");
        btnTradeInNumbers.setText("Zestawienie w liczbach");
        btnLeft.setVisibility(View.INVISIBLE);
        btnRight.setVisibility(View.INVISIBLE);
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
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_calculator_of_field));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
}


