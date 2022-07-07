package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;

public class MonthlyWeightFromPepperChartFragment extends Fragment {

    Context context;
    Fragment fragment = null;

    BarChart chart;
    Button btnTradeInNumbers;
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
        ArrayList<BarEntry> months = new ArrayList<>();
        months.add(new BarEntry(6, (float) calculateWeightFromHighgrove(6)));
        months.add(new BarEntry(7, (float) calculateWeightFromHighgrove(7)));
        months.add(new BarEntry(8, (float) calculateWeightFromHighgrove(8)));
        months.add(new BarEntry(9, (float) calculateWeightFromHighgrove(9)));
        months.add(new BarEntry(10, (float) calculateWeightFromHighgrove(10)));
        months.add(new BarEntry(11, (float) calculateWeightFromHighgrove(11)));


        BarDataSet barDataSet = new BarDataSet(months,"Dane");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        barDataSet.setBarBorderColor(getResources().getColor(R.color.blackToWhite));
        barDataSet.setValueTextSize(15f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(getResources().getColor(R.color.blackToWhite));
        xAxis.setAxisLineWidth(2f);
        xAxis.setGridLineWidth(2f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextSize(15f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(Math.round(value)==6)
                    return "Czerwiec";
                else if(Math.round(value)==7)
                    return "Lipiec";
                else if(Math.round(value)==8)
                    return "Sierpień";
                else if(Math.round(value)==9)
                    return "Wrzesień";
                else if(Math.round(value)==10)
                    return "Październik";
                else if(Math.round(value)==11)
                    return "Listopad";
                else
                    return "";
            }
        });
        YAxis yAxisl = chart.getAxisLeft();
        yAxisl.setTextColor(getResources().getColor(R.color.blackToWhite));
        yAxisl.setAxisLineWidth(2f);
        yAxisl.setGridLineWidth(2f);
        yAxisl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value=value/1000;
                return Math.round(value) + "tyś.kg";
            }
        });

        YAxis yAxisp = chart.getAxisRight();
        yAxisp.setTextColor(getResources().getColor(R.color.blackToWhite));
        yAxisp.setAxisLineWidth(2f);
        yAxisp.setGridLineWidth(2f);
        yAxisp.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value=value/1000;
                return Math.round(value) + "tyś.kg";
            }
        });

        BarData barData = new BarData(barDataSet);


        chart.setFitBars(true);
        chart.getLegend().setEnabled(false);
        chart.setData(barData);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
    }

    private double calculateWeightFromHighgrove(int month) {
        DataBaseHelper db= new DataBaseHelper(context);
        Date calendar = new Date();
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromTrade();
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(getYear(date) == calendar.getYear()+1900)
                if(getMonth(date)==month)
                    sum=sum+=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));

        }
        return sum;
    }

    private int getMonth(String date) {
        char[] charDate = date.toCharArray();
        String stringMonth = Character.toString(charDate[3]) + Character.toString(charDate[4]);
        int month = Integer.parseInt(stringMonth);
        return month;
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
                        fragment = new TradeStatisticsFragment();
                    }break;
                    case R.id.btn_left:
                    {
                        fragment=new ClassOfPepperChartFragment();
                    }break;
                    case R.id.btn_right:
                    {
                        fragment=new PriceOfPepperChart();
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
        chart=view.findViewById(R.id.chart);
        btnTradeInNumbers=view.findViewById(R.id.btn_trade_in_numbers);
        btnLeft=view.findViewById(R.id.btn_left);
        btnRight=view.findViewById(R.id.btn_right);
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




