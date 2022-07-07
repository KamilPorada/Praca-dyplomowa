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
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class BalanceFragment extends Fragment {

    private Fragment fragment = null;
    private Context context;

    private PieChart chart;
    private Button outgoingsButton, tradeButton;

    float moneyFromTrades, moneyFromOutgoings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_balance, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
        calculateMoney();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_balance));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        outgoingsButton=view.findViewById(R.id.btn_outgoings);
        tradeButton=view.findViewById(R.id.btn_trade);
        chart=view.findViewById(R.id.chart);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
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
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        tradeButton.setOnClickListener(listener);
        outgoingsButton.setOnClickListener(listener);
    }

    private void calculateMoney() {
        moneyFromTrades= StatisticsHelper.getMoneyFromTrade(context, ToolClass.getActualYear());
        moneyFromOutgoings=StatisticsHelper.getMoneyFromOutgoings(context, ToolClass.getActualYear());
    }

    private void createChart() {
        ArrayList<PieEntry> colors = new ArrayList<>();
        colors.add(new PieEntry((moneyFromTrades),"Dochody"));
        colors.add(new PieEntry((moneyFromOutgoings),"Wydatki"));

        final int [] colorsOfPie = {
                ContextCompat.getColor(context, R.color.mainColor),
                ContextCompat.getColor(context, R.color.red)
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
                return "±" + Math.round(value / 1000) + " tyś. zł";
            }
        });


        chart.setData(pieData);
        chart.setDrawHoleEnabled(true);
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


