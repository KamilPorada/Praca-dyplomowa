package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class MonthlyWeightFromPepperChartFragment extends Fragment {

    private Context context;
    private Fragment fragment = null;

    private BarChart chart;
    private Button btnTradeInNumbers;
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculator_of_field));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        chart=view.findViewById(R.id.chart);
        btnTradeInNumbers=view.findViewById(R.id.btn_trade_in_numbers);
        btnLeft=view.findViewById(R.id.btn_left);
        btnRight=view.findViewById(R.id.btn_right);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
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
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnTradeInNumbers.setOnClickListener(listener);
        btnLeft.setOnClickListener(listener);
        btnRight.setOnClickListener(listener);
    }

    private void createChart() {
        ArrayList<BarEntry> months = new ArrayList<>();
        months.add(new BarEntry(6, (float) StatisticsHelper.calculateMonthlyWeightFromHighgrove(context,6, ToolClass.getActualYear())));
        months.add(new BarEntry(7, (float) StatisticsHelper.calculateMonthlyWeightFromHighgrove(context,7, ToolClass.getActualYear())));
        months.add(new BarEntry(8, (float) StatisticsHelper.calculateMonthlyWeightFromHighgrove(context,8, ToolClass.getActualYear())));
        months.add(new BarEntry(9, (float) StatisticsHelper.calculateMonthlyWeightFromHighgrove(context,9, ToolClass.getActualYear())));
        months.add(new BarEntry(10, (float) StatisticsHelper.calculateMonthlyWeightFromHighgrove(context,10, ToolClass.getActualYear())));
        months.add(new BarEntry(11, (float) StatisticsHelper.calculateMonthlyWeightFromHighgrove(context,11, ToolClass.getActualYear())));


        BarDataSet barDataSet = new BarDataSet(months,"Dane");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        barDataSet.setBarBorderColor(ContextCompat.getColor(context, R.color.blackToWhite));
        barDataSet.setValueTextSize(15f);

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
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
        yAxisl.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
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
        yAxisp.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        yAxisp.setAxisLineWidth(2f);
        yAxisp.setGridLineWidth(2f);
        yAxisp.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value=value/1000;
                return Math.round(value) + "tyś.kg";
            }
        });

        chart.setFitBars(true);
        chart.getLegend().setEnabled(false);
        chart.setData(barData);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
    }
}




