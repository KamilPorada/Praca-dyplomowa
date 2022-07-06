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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;

public class OutgoingsChartFragment extends Fragment {

    private Context context;
    private Fragment fragment = null;

    private TextView title;
    private BarChart chart;
    private Button btnOutgoingsInNumbers;
    ImageView btnLeft, btnRight;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_outgoings_monthly_weight_from_pepper_chart_fragment, container, false);
        findViews(view);
        createListeners();
        createChart();
        return view;
    }

    private void createChart() {
        ArrayList<BarEntry> category1 = new ArrayList<>();
        category1.add(new BarEntry(1, (float) calculateSpecificOutgoing("Konstrukcje tuneli")));
        ArrayList<BarEntry> category2 = new ArrayList<>();
        category2.add(new BarEntry(2, (float) calculateSpecificOutgoing("Folie ogrodnicze")));
        ArrayList<BarEntry> category3 = new ArrayList<>();
        category3.add(new BarEntry(3, (float) calculateSpecificOutgoing("Hydraulika w tunelach")));
        ArrayList<BarEntry> category4 = new ArrayList<>();
        category4.add(new BarEntry(4, (float) calculateSpecificOutgoing("Paliki do tuneli")));
        ArrayList<BarEntry> category5 = new ArrayList<>();
        category5.add(new BarEntry(5, (float) calculateSpecificOutgoing("Nasiona papryki")));
        ArrayList<BarEntry> category6 = new ArrayList<>();
        category6.add(new BarEntry(6, (float) calculateSpecificOutgoing("Sadzonki papryki")));
        ArrayList<BarEntry> category7 = new ArrayList<>();
        category7.add(new BarEntry(7, (float) calculateSpecificOutgoing("Pestycydy")));
        ArrayList<BarEntry> category8 = new ArrayList<>();
        category8.add(new BarEntry(8, (float) calculateSpecificOutgoing("Nawozy")));
        ArrayList<BarEntry> category9 = new ArrayList<>();
        category9.add(new BarEntry(9, (float) calculateSpecificOutgoing("Maszyny rolnicze")));
        ArrayList<BarEntry> category10 = new ArrayList<>();
        category10.add(new BarEntry(10, (float) calculateSpecificOutgoing("Narzędzia ogrodnicze")));
        ArrayList<BarEntry> category11 = new ArrayList<>();
        category11.add(new BarEntry(11, (float) calculateSpecificOutgoing("Inne")));

        final int [] Colors = {
                Color.rgb(162, 232, 114), Color.rgb(242, 199, 119), Color.rgb(219, 118, 142),
                Color.rgb(242, 221, 211), Color.rgb(154, 0, 219), Color.rgb(7, 177, 245),
                Color.rgb(80, 140, 140), Color.rgb(160, 180, 160), Color.rgb(180, 180, 40),
                Color.rgb(188, 185, 174), Color.rgb(220, 0, 128)
        };

        BarDataSet barDataSet1 = new BarDataSet(category1,"Konstrukcje tuneli");
        barDataSet1.setColors(Colors[0]);
        barDataSet1.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet1.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet1.setValueTextSize(15f);

        BarDataSet barDataSet2 = new BarDataSet(category2,"Folie ogrodnicze");
        barDataSet2.setColors(Colors[1]);
        barDataSet2.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet2.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet2.setValueTextSize(15f);

        BarDataSet barDataSet3 = new BarDataSet(category3,"Hydraulika w tunelach");
        barDataSet3.setColors(Colors[2]);
        barDataSet3.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet3.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet3.setValueTextSize(15f);

        BarDataSet barDataSet4 = new BarDataSet(category4,"Paliki do tuneli");
        barDataSet4.setColors(Colors[3]);
        barDataSet4.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet4.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet4.setValueTextSize(15f);

        BarDataSet barDataSet5 = new BarDataSet(category5,"Nasiona papryki");
        barDataSet5.setColors(Colors[4]);
        barDataSet5.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet5.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet5.setValueTextSize(15f);

        BarDataSet barDataSet6 = new BarDataSet(category6,"Sadzonki papryki");
        barDataSet6.setColors(Colors[5]);
        barDataSet6.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet6.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet6.setValueTextSize(15f);

        BarDataSet barDataSet7 = new BarDataSet(category7,"Pestycydy");
        barDataSet7.setColors(Colors[6]);
        barDataSet7.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet7.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet7.setValueTextSize(15f);

        BarDataSet barDataSet8 = new BarDataSet(category8,"Nawozy");
        barDataSet8.setColors(Colors[7]);
        barDataSet8.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet8.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet8.setValueTextSize(15f);

        BarDataSet barDataSet9 = new BarDataSet(category9,"Maszyny rolnicze");
        barDataSet9.setColors(Colors[8]);
        barDataSet9.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet9.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet9.setValueTextSize(15f);

        BarDataSet barDataSet10 = new BarDataSet(category10,"Narzędzia ogrodnicze");
        barDataSet10.setColors(Colors[9]);
        barDataSet10.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet10.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet10.setValueTextSize(15f);

        BarDataSet barDataSet11 = new BarDataSet(category11,"Inne");
        barDataSet11.setColors(Colors[10]);
        barDataSet11.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet11.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet11.setValueTextSize(15f);


        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);
        YAxis yAxisl = chart.getAxisLeft();
        yAxisl.setAxisLineWidth(2f);
        yAxisl.setGridLineWidth(2f);
        yAxisl.setTextColor(getResources().getColor(R.color.blackToWhite));
        yAxisl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value = value/1000;
                return Math.round(value) + " tyś. zł";
            }
        });

        YAxis yAxisp = chart.getAxisRight();
        yAxisp.setEnabled(false);

        BarData barData =  new BarData();
        barData.addDataSet(barDataSet1);
        barData.addDataSet(barDataSet2);
        barData.addDataSet(barDataSet3);
        barData.addDataSet(barDataSet4);
        barData.addDataSet(barDataSet5);
        barData.addDataSet(barDataSet6);
        barData.addDataSet(barDataSet7);
        barData.addDataSet(barDataSet8);
        barData.addDataSet(barDataSet9);
        barData.addDataSet(barDataSet10);
        barData.addDataSet(barDataSet11);

       barData.setValueTextSize(0f);


        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setTextColor(getResources().getColor(R.color.blackToWhite));
        l.setTextSize(10f);

        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);



    }

    private double calculateSpecificOutgoing(String category) {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromSpecificOutgoing(category);
        Date calendar = new Date();
        int currentYear=calendar.getYear()+1900;
        int year=0;
        double totalMoney=0;
        String date="";
        while (k.moveToNext())
        {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            year=getYear(date);
            if(currentYear==year)
                totalMoney=totalMoney+k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
        }
        return totalMoney;
    }

    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
    }

    private void createListeners() {
        btnOutgoingsInNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new OutgoingsStatisticsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }



    private void findViews(View view) {
        title=view.findViewById(R.id.title);
        chart=view.findViewById(R.id.chart);
        btnOutgoingsInNumbers=view.findViewById(R.id.btn_trade_in_numbers);
        btnLeft=view.findViewById(R.id.btn_left);
        btnRight=view.findViewById(R.id.btn_right);
        btnOutgoingsInNumbers.setText("Wydatki w liczbach");
        title.setText("Wykres przedstawiający wydatki poszczególnych kategorii w bieżącym sezonie");
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




