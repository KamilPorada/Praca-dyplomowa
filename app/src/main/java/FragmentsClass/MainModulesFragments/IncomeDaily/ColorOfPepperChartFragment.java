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

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;

public class ColorOfPepperChartFragment extends Fragment {

    Context context;
    Fragment fragment = null;

    TextView title;
    PieChart chart;
    Button btnTradeInNumbers;
    ImageView btnLeft, btnRight;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_summary_colors_class_of_pepper_chart_fragment, container, false);
        findViews(view);
        createListeners();
        createChart();
        return view;
    }

    private void createChart() {
        ArrayList<PieEntry> colors = new ArrayList<>();
        colors.add(new PieEntry((calculateWeightFromColorOfPepper("czerwona")),"czerwona"));
        colors.add(new PieEntry((calculateWeightFromColorOfPepper("żółta")),"żółta"));
        colors.add(new PieEntry((calculateWeightFromColorOfPepper("zielona")),"zielona"));
        colors.add(new PieEntry((calculateWeightFromColorOfPepper("pomarańczowa")),"pomarańczowa"));
        colors.add(new PieEntry((calculateWeightFromColorOfPepper("blondyna")),"blondyna"));

        final int [] ColorOfPepper = {
                Color.rgb(255,58,42), Color.rgb(255,204,0),
                Color.rgb(0,128,0), Color.rgb(253,121,19),
                Color.rgb(218,213,140)
        };

        PieDataSet pieDataSet = new PieDataSet(colors,"");
        pieDataSet.setColors(ColorOfPepper);
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
        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextSize(15f);
        chart.setCenterTextSize(20f);
        chart.setHoleColor(getResources().getColor(R.color.backgroundColor));
        chart.getLegend().setTextColor(getResources().getColor(R.color.blackToWhite));
        chart.setCenterTextColor(getResources().getColor(R.color.blackToWhite));
        chart.animateY(1000 , Easing.EaseInCirc);

    }

    private float calculateWeightFromColorOfPepper(String color) {
        DataBaseHelper db = new DataBaseHelper(context);
        Date calendar = new Date();
        String date;
        double sum=0;
        Cursor cursor = db.getWeightFromColor(color);
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            if(getYear(date) == calendar.getYear()+1900)
                sum=sum+=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
        }
        return (float) sum;
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
                        fragment = new PriceOfPepperChart();
                    }break;
                    case R.id.btn_right:
                    {
                        fragment=new ClassOfPepperChartFragment();
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
    title.setText("Wykres przedstawiający procentową ilość sprzedaży w zależności od koloru papryki");
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


