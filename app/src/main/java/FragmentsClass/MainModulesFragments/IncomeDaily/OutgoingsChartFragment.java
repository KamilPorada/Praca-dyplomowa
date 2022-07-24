package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.core.content.ContextCompat;
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

import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;

public class OutgoingsChartFragment extends Fragment {

    private Context context;
    private Fragment fragment = null;

    private TextView title;
    private BarChart chart;
    private Button btnOutgoingsInNumbers;
    private ImageView btnLeft, btnRight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_outgoings_monthly_weight_from_pepper_chart_fragment, container, false);
        findViews(view);
        createListeners();
        createChart();
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
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_outgoings_chart));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void findViews(View view) {
        title=view.findViewById(R.id.title);
        chart=view.findViewById(R.id.chart);
        btnOutgoingsInNumbers=view.findViewById(R.id.btn_trade_in_numbers);
        btnLeft=view.findViewById(R.id.btn_left);
        btnRight=view.findViewById(R.id.btn_right);
        btnOutgoingsInNumbers.setText("Wydatki w liczbach");
        title.setText("Wykres przedstawiający\nwydatki poszczególnych\nkategorii w bieżącym sezonie");
        btnLeft.setVisibility(View.INVISIBLE);
        btnRight.setVisibility(View.INVISIBLE);
    }

    private void createListeners() {
        btnOutgoingsInNumbers.setOnClickListener(v -> {
            fragment=new OutgoingsStatisticsFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        });
    }

    private void createChart() {
        final int [] colors = {
                Color.rgb(162, 232, 114), Color.rgb(242, 199, 119), Color.rgb(219, 118, 142),
                Color.rgb(242, 221, 211), Color.rgb(154, 0, 219), Color.rgb(7, 177, 245),
                Color.rgb(80, 140, 140), Color.rgb(160, 180, 160), Color.rgb(180, 180, 40),
                Color.rgb(188, 185, 174), Color.rgb(220, 0, 128)
        };

        final String [] categories ={
                "Konstrukcje tuneli", "Folie ogrodnicze", "Hydraulika w tunelach",
                "Paliki do tuneli", "Nasiona papryki", "Sadzonki papryki", "Pestycydy", "Nawozy",
                "Maszyny rolnicze", "Narzędzia ogrodnicze", "Inne"
        };

        ArrayList<BarEntry> category1 = new ArrayList<>();
        category1.add(new BarEntry(1, StatisticsHelper.calculateSpecificOutgoing(context,categories[0])));
        ArrayList<BarEntry> category2 = new ArrayList<>();
        category2.add(new BarEntry(2,  StatisticsHelper.calculateSpecificOutgoing(context,categories[1])));
        ArrayList<BarEntry> category3 = new ArrayList<>();
        category3.add(new BarEntry(3,  StatisticsHelper.calculateSpecificOutgoing(context,categories[2])));
        ArrayList<BarEntry> category4 = new ArrayList<>();
        category4.add(new BarEntry(4,  StatisticsHelper.calculateSpecificOutgoing(context,categories[3])));
        ArrayList<BarEntry> category5 = new ArrayList<>();
        category5.add(new BarEntry(5,  StatisticsHelper.calculateSpecificOutgoing(context,categories[4])));
        ArrayList<BarEntry> category6 = new ArrayList<>();
        category6.add(new BarEntry(6,  StatisticsHelper.calculateSpecificOutgoing(context,categories[5])));
        ArrayList<BarEntry> category7 = new ArrayList<>();
        category7.add(new BarEntry(7, StatisticsHelper.calculateSpecificOutgoing(context,categories[6])));
        ArrayList<BarEntry> category8 = new ArrayList<>();
        category8.add(new BarEntry(8,  StatisticsHelper.calculateSpecificOutgoing(context,categories[7])));
        ArrayList<BarEntry> category9 = new ArrayList<>();
        category9.add(new BarEntry(9, StatisticsHelper.calculateSpecificOutgoing(context,categories[8])));
        ArrayList<BarEntry> category10 = new ArrayList<>();
        category10.add(new BarEntry(10,  StatisticsHelper.calculateSpecificOutgoing(context,categories[9])));
        ArrayList<BarEntry> category11 = new ArrayList<>();
        category11.add(new BarEntry(11,  StatisticsHelper.calculateSpecificOutgoing(context,categories[10])));

        BarDataSet [] dataSet = {
                new BarDataSet(category1, categories[0]),
                new BarDataSet(category2, categories[1]),
                new BarDataSet(category3, categories[2]),
                new BarDataSet(category4, categories[3]),
                new BarDataSet(category5, categories[4]),
                new BarDataSet(category6, categories[5]),
                new BarDataSet(category7, categories[6]),
                new BarDataSet(category8, categories[7]),
                new BarDataSet(category9, categories[8]),
                new BarDataSet(category10, categories[9]),
                new BarDataSet(category11, categories[10])
        };

        for(int i=0;i<dataSet.length;i++)
        {
            dataSet[i].setColors(colors[i]);
            dataSet[i].setValueTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
            dataSet[i].setBarBorderColor(ContextCompat.getColor(context, R.color.blackToWhite));
            dataSet[i].setValueTextSize(15f);
        }

        BarData barData =  new BarData();
        for (BarDataSet barDataSet : dataSet) barData.addDataSet(barDataSet);
        barData.setValueTextSize(0f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);
        YAxis yAxisl = chart.getAxisLeft();
        yAxisl.setAxisLineWidth(2f);
        yAxisl.setGridLineWidth(2f);
        yAxisl.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        yAxisl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value = value/1000;
                return Math.round(value) + " tyś. zł";
            }
        });

        YAxis yAxisp = chart.getAxisRight();
        yAxisp.setEnabled(false);

        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        l.setTextSize(10f);

        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
    }
}




