package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;

public class PriceOfPepperChart extends Fragment {

    Context context;
    Fragment fragment = null;

    private TextView title;
    private LineChart chart;
    private Button btnTradeInNumbers, btnChangeProperties;
    private ImageView btnLeft, btnRight;

    //--------------------------------------CHOOSE WINDOW VIEWS--------------------------------------------//

    private RadioGroup colorRadioGroup, classRadiogroup;
    private Button acceptButton;
    private String color="czerwona", clas="1";
    private String describeColor="czerwonej", describeClas="1";
    private int lineColor=Color.rgb(255,58,42);



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_price_of_pepper_chart, container, false);
        findViews(view);
        createListeners();
        createChart();
        return view;
    }

    private void openChooseWindow() {
        Dialog chooseWindow = new Dialog(context);
        chooseWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        chooseWindow.setContentView(R.layout.dialog_choose);
        chooseWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseWindow.show();
        color="czerwona";
        clas="1";
        lineColor=Color.rgb(255,58,42);
        findAddItemDialogViews(chooseWindow);
        createAndAddListeners(chooseWindow);
    }

    private void createAndAddListeners(Dialog chooseWindow) {
        colorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.red_color:
                    {
                        color="czerwona";
                        describeColor="czerwonej";
                        lineColor=Color.rgb(255,58,42);
                    }break;
                    case R.id.yellow_color:
                    {
                        color="żółta";
                        describeColor="żółtej";
                        lineColor=Color.rgb(255,204,0);
                    }break;
                    case R.id.green_color:
                    {
                        color="zielona";
                        describeColor="zielonej";
                        lineColor=Color.rgb(0,128,0);
                    }break;
                    case R.id.orange_color:
                    {
                        color="pomarańczowa";
                        describeColor="pomarańczowej";
                        lineColor=Color.rgb(253,121,19);
                    }break;
                    case R.id.blond_color:
                    {
                        color="blondyna";
                        describeColor="blondyny";
                        lineColor=Color.rgb(218,213,140);
                    }break;
                }
            }
        });

        classRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.first_class:
                    {
                        clas="1";
                        describeClas="1";
                    }break;
                    case R.id.second_class:
                    {
                        clas="2";
                        describeClas="2";
                    }break;
                    case R.id.cutting_class:
                    {
                        clas="krojona";
                        describeClas="krojonej";
                    }break;
                }
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWindow.dismiss();
                updateTitle();
                createChart();
            }
        });

    }

    private void updateTitle() {
        title.setText("Wykres przedstawiający kształtowanie się cen papryki " + describeColor + " klasy " + describeClas);
    }

    private void findAddItemDialogViews(Dialog chooseWindow) {
        colorRadioGroup=chooseWindow.findViewById(R.id.color_radio_group);
        classRadiogroup=chooseWindow.findViewById(R.id.class_radio_group);
        acceptButton=chooseWindow.findViewById(R.id.accept_button);
    }

    private void createChart() {
        ArrayList <Entry> prices = new ArrayList<>();
        int[] d = new int[1000];
        float[] p = new float[1000];
        int pointer=0;
        double sumJune=0, sumJuly=0, sumAugust=0, sumSeptember=0, sumOctober=0, sumNovember=0;
        double weightJune=0, weightJuly=0, weightAugust=0, weightSeptember=0, weightOctober=0, weightNovember=0;
        double answerJune=0, answerJuly=0, answerAugust=0, answerSeptember=0, answerOctober=0, answerNovember=0;
        double avgPrice=0;

        DataBaseHelper db = new DataBaseHelper(context);
        Date calendar = new Date();
        String date;
        String stringVat;
        int vat;
        double price;
        double weight;
        Cursor cursor = db.getPriceWeightAndDateFromTrade(color,clas);
        while (cursor.moveToNext())
        {
            date=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            vat=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_VAT));
            price=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
            weight=cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
            stringVat="0.0"+vat;
            if(getYear(date) == calendar.getYear()+1900)
            {
                if(getMonth(date)==6)
                {
                    sumJune = sumJune + (Double.parseDouble(stringVat)+1)*price*weight;
                    weightJune = weightJune + weight;
                }
                if(getMonth(date)==7)
                {
                    sumJuly = sumJuly + (Double.parseDouble(stringVat)+1)*price*weight;
                    weightJuly = weightJuly + weight;
                }
                if(getMonth(date)==8)
                {
                    sumAugust = sumAugust + (Double.parseDouble(stringVat)+1)*price*weight;
                    weightAugust = weightAugust + weight;
                }
                if(getMonth(date)==9)
                {
                    sumSeptember = sumSeptember + (Double.parseDouble(stringVat)+1)*price*weight;
                    weightSeptember = weightSeptember + weight;
                }
                if(getMonth(date)==10)
                {
                    sumOctober = sumOctober + (Double.parseDouble(stringVat)+1)*price*weight;
                    weightOctober = weightOctober + weight;
                }
                if(getMonth(date)==11)
                {
                    sumNovember = sumNovember + (Double.parseDouble(stringVat)+1)*price*weight;
                    weightNovember = weightNovember + weight;
                }
            }
        }
        if(weightJune>0)
            answerJune=sumJune/weightJune;
        else 
            answerJune=0;
        if(weightJuly>0)
            answerJuly=sumJuly/weightJuly;
        else
            answerJuly=0;
        if(weightAugust>0)
            answerAugust=sumAugust/weightAugust;
        else
            answerAugust=0;
        if(weightSeptember>0)
            answerSeptember=sumSeptember/weightSeptember;
        else
            answerSeptember=0;
        if(weightOctober>0)
            answerOctober=sumOctober/weightOctober;
        else
            answerOctober=0;
        if(weightNovember>0)
            answerNovember=sumNovember/weightNovember;
        else
            answerNovember=0;

        int numb=0;
        
        if(answerJune>0) {
            avgPrice = avgPrice + answerJune;
            numb++;
        }
        if(answerJuly>0) {
            avgPrice = avgPrice + answerJuly;
            numb++;
        }
        if(answerAugust>0) {
            avgPrice = avgPrice + answerAugust;
            numb++;
        }
        if(answerSeptember>0) {
            avgPrice = avgPrice + answerSeptember;
            numb++;
        }
        if(answerOctober>0) {
            avgPrice = avgPrice + answerOctober;
            numb++;
        }
        if(answerNovember>0) {
            avgPrice = avgPrice + answerNovember;
            numb++;
        }

        avgPrice=avgPrice/numb;

        System.out.println(avgPrice);
            
        prices.add(new Entry((float) 6, (float) (answerJune)));
        prices.add(new Entry(7, (float) (answerJuly)));
        prices.add(new Entry(8, (float) (answerAugust)));
        prices.add(new Entry(9, (float) (answerSeptember)));
        prices.add(new Entry(10, (float) (answerOctober)));
        prices.add(new Entry(11, (float) (answerNovember)));
        System.out.println(prices);




        LineDataSet lineDataSet = new LineDataSet(prices,"");
        lineDataSet.setColors(lineColor);
        lineDataSet.setValueTextColor(getResources().getColor(R.color.blackToWhite));
        lineDataSet.setValueTextSize(0f);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawCircleHole(false);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        ArrayList <ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LimitLine averagePrice = new LimitLine((float) avgPrice, "Średnia cena: "+String.format("%.2f", Math.round(avgPrice * 100.0) / 100.0) + "zł");
        averagePrice.setLineWidth(3f);
        averagePrice.enableDashedLine(10.0f,10.0f,0.0f);
        averagePrice.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        averagePrice.setTextSize(15f);
        averagePrice.setTextColor(getResources().getColor(R.color.blackToWhite));
        averagePrice.setLineColor(lineColor);


        XAxis xaxis =chart.getXAxis();
        xaxis.setTextColor(getResources().getColor(R.color.blackToWhite));
        xaxis.setGridColor(getResources().getColor(R.color.blackToWhite));
        xaxis.setAxisLineColor(getResources().getColor(R.color.blackToWhite));
        xaxis.setAxisLineWidth(2f);
        xaxis.setTextSize(15f);
        xaxis.setLabelRotationAngle(-45);
        xaxis.setAxisMinimum(6.0f);
        xaxis.setAxisMaximum(11.0f);
        xaxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(value>=6 && value<6.5)
                    return "Czerwiec";
                else if(value>=7 && value<7.5)
                    return "Lipiec";
                else if(value>=8 && value<8.5)
                    return "Sierpień";
                else if(value>=8.5 && value<9)
                    return "Wrzesień";
                else if(value>=9 && value<10)
                    return "Październik";
                else if(value>=10)
                    return "Listopad";
                else
                    return "";
            }
        });

        YAxis yAxisl = chart.getAxisLeft();
        yAxisl.setTextColor(getResources().getColor(R.color.blackToWhite));
        yAxisl.setGridColor(getResources().getColor(R.color.blackToWhite));
        yAxisl.setAxisLineColor(getResources().getColor(R.color.blackToWhite));
        yAxisl.setAxisLineWidth(1f);
        yAxisl.setTextSize(12f);
        yAxisl.removeAllLimitLines();
        yAxisl.addLimitLine(averagePrice);
        yAxisl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.format("%.2f", Math.round(value * 100.0) / 100.0) + "zł";
            }
        });

        YAxis yAxisp = chart.getAxisRight();
        yAxisp.setTextColor(getResources().getColor(R.color.blackToWhite));
        yAxisp.setGridColor(getResources().getColor(R.color.blackToWhite));
        yAxisp.setAxisLineColor(getResources().getColor(R.color.blackToWhite));
        yAxisp.setAxisLineWidth(2f);
        yAxisp.setTextSize(12f);
        yAxisp.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.format("%.2f", Math.round(value * 100.0) / 100.0) + "zł";
            }
        });

        LineData lineData = new LineData(dataSets);

        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.animateY(500);



    }

    private int getDayAndMonth(String date) {
        char[] charDate = date.toCharArray();
        String stringDay = Character.toString(charDate[0]) + Character.toString(charDate[1]);
        String stringMonth = Character.toString(charDate[3]) + Character.toString(charDate[4]);
        String answer;
        stringMonth=stringMonth+"00";
        answer=stringMonth+stringDay;
        return Integer.parseInt(answer);
    }

    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
    }

    private int getMonth(String date) {
        char[] charDate = date.toCharArray();
        String stringMonth = Character.toString(charDate[3]) + Character.toString(charDate[4]);
        int month = Integer.parseInt(stringMonth);
        return month;
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
                        fragment = new MonthlyWeightFromPepperChartFragment();
                    }break;
                    case R.id.btn_right:
                    {
                        fragment = new ColorOfPepperChartFragment();
                    }break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        };
        btnTradeInNumbers.setOnClickListener(listener);
        btnLeft.setOnClickListener(listener);
        btnRight.setOnClickListener(listener);

        btnChangeProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooseWindow();
            }
        });
    }



    private void findViews(View view) {
    title=view.findViewById(R.id.title);
    chart=view.findViewById(R.id.chart);
    btnChangeProperties=view.findViewById(R.id.btn_change_properties);
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


