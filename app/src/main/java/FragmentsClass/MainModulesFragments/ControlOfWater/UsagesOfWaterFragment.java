package FragmentsClass.MainModulesFragments.ControlOfWater;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;

import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class UsagesOfWaterFragment extends Fragment {

    private Context context;

    private ImageView buttonComeBack;
    private BarChart chart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_usages_of_water, container, false);
        assert container != null;
        context=container.getContext();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_usages_of_water));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        buttonComeBack=view.findViewById(R.id.button_come_back);
        chart=view.findViewById(R.id.chart);
    }

    private void createListeners() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ControlOfWaterFragment()).commit());
    }

    private void createChart() {
        ArrayList<BarEntry> months = new ArrayList<>();

        months.add(new BarEntry(4, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,4, ToolClass.getActualYear())));
        months.add(new BarEntry(5, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,5, ToolClass.getActualYear())));
        months.add(new BarEntry(6, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,6, ToolClass.getActualYear())));
        months.add(new BarEntry(7, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,7, ToolClass.getActualYear())));
        months.add(new BarEntry(8, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,8, ToolClass.getActualYear())));
        months.add(new BarEntry(9, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,9, ToolClass.getActualYear())));
        months.add(new BarEntry(10, (float) StatisticsHelper.calculateMonthlyUsagesOfWater(context,10, ToolClass.getActualYear())));

        BarDataSet barDataSet = new BarDataSet(months,"Dane");
        barDataSet.setColors(Color.rgb(0,255,255));
        barDataSet.setValueTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        barDataSet.setBarBorderColor(ContextCompat.getColor(context, R.color.blackToWhite));
        barDataSet.setValueTextSize(15f);

        BarData barData = new BarData(barDataSet);
        barData.setValueTextSize(12f);
        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int amount = Math.round(value/1000);
                return amount + " tyś.l";
            }
        });


        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        xAxis.setAxisLineWidth(1f);
        xAxis.setGridColor(ContextCompat.getColor(context, R.color.backgroundColor));
        xAxis.setGridLineWidth(0f);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextSize(15f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(Math.round(value)==4)
                    return "Kwiecień";
                else if(Math.round(value)==5)
                    return "Maj";
                else if(Math.round(value)==6)
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
        yAxisl.setGridLineWidth(0.2f);
        yAxisl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value=value/1000;
                return Math.round(value) + "tyś.l";
            }
        });

        YAxis yAxisp = chart.getAxisRight();
        yAxisp.setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        yAxisp.setAxisLineWidth(2f);
        yAxisp.setGridLineWidth(0.2f);
        yAxisp.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                value=value/1000;
                return Math.round(value) + "tyś.l";
            }
        });

        chart.setFitBars(true);
        chart.getLegend().setEnabled(false);
        chart.setData(barData);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
    }

}


