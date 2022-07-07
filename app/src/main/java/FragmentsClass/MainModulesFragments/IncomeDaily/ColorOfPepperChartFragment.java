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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class ColorOfPepperChartFragment extends Fragment {

    private Context context;
    private Fragment fragment = null;

    private TextView title;
    private PieChart chart;
    private Button btnTradeInNumbers;
    private ImageView btnLeft, btnRight;

    private final String[] colorsOfPepper = {
      "czerwona", "żółta", "zielona", "pomarańczowa", "blondyna"
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_summary_colors_class_of_pepper_chart_fragment, container, false);
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

    @SuppressLint("SetTextI18n")
    private void findViews(View view) {
        title=view.findViewById(R.id.title);
        chart=view.findViewById(R.id.chart);
        btnTradeInNumbers=view.findViewById(R.id.btn_trade_in_numbers);
        btnLeft=view.findViewById(R.id.btn_left);
        btnRight=view.findViewById(R.id.btn_right);
        title.setText("Wykres przedstawiający procentową ilość sprzedaży w zależności od koloru papryki");
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
                    fragment = new PriceOfPepperChart();
                }break;
                case R.id.btn_right:
                {
                    fragment=new ClassOfPepperChartFragment();
                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnTradeInNumbers.setOnClickListener(listener);
        btnLeft.setOnClickListener(listener);
        btnRight.setOnClickListener(listener);
    }

    private void createChart() {
        ArrayList<PieEntry> colors = new ArrayList<>();
        colors.add(new PieEntry((float) StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),colorsOfPepper[0]),"czerwona"));
        colors.add(new PieEntry((float) StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),colorsOfPepper[1]),"żółta"));
        colors.add(new PieEntry((float) StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),colorsOfPepper[2]),"zielona"));
        colors.add(new PieEntry((float) StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),colorsOfPepper[3]),"pomarańczowa"));
        colors.add(new PieEntry((float) StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),colorsOfPepper[4]),"blondyna"));

        final int [] ColorOfPepper = {
                Color.rgb(255,58,42), Color.rgb(255,204,0),
                Color.rgb(0,128,0), Color.rgb(253,121,19),
                Color.rgb(218,213,140)
        };

        PieDataSet pieDataSet = new PieDataSet(colors,"");
        pieDataSet.setColors(ColorOfPepper);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(20f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(chart));

        Legend l = chart.getLegend();
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        chart.setUsePercentValues(true);
        chart.setDrawHoleEnabled(true);

        chart.setData(pieData);
        chart.invalidate();
        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextSize(15f);
        chart.setCenterTextSize(20f);
        chart.setHoleColor(ContextCompat.getColor(context, R.color.backgroundColor));
        chart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        chart.setCenterTextColor(ContextCompat.getColor(context, R.color.blackToWhite));
        chart.animateY(1000 , Easing.EaseInCirc);
    }
}


