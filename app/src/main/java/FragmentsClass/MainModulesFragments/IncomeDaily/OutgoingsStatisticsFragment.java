package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;

public class OutgoingsStatisticsFragment extends Fragment {

    private Fragment fragment = null;
    private Context context;

    private TextView highgroves, foils, water, sticks, seeds, plants, pesticides, fertilizers, machines, tools,
                     others, totalSum;
    private Button btnOutgoingInChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_statistics_of_outgoings, container, false);
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
        return view;
    }

    private void loadData() {
        highgroves.setText(calculateSpecificOutgoing("Konstrukcje tuneli") + "zł");
        foils.setText(calculateSpecificOutgoing("Folie ogrodnicze") + "zł");
        water.setText(calculateSpecificOutgoing("Hydraulika w tunelach") + "zł");
        sticks.setText(calculateSpecificOutgoing("Paliki do tuneli") + "zł");
        seeds.setText(calculateSpecificOutgoing("Nasiona papryki") + "zł");
        plants.setText(calculateSpecificOutgoing("Sadzonki papryki") + "zł");
        pesticides.setText(calculateSpecificOutgoing("Pestycydy") + "zł");
        fertilizers.setText(calculateSpecificOutgoing("Nawozy") + "zł");
        machines.setText(calculateSpecificOutgoing("Maszyny rolnicze") + "zł");
        tools.setText(calculateSpecificOutgoing("Narzędzia ogrodnicze") + "zł");
        others.setText(calculateSpecificOutgoing("Inne") + "zł");
        totalSum.setText(calculateTotalSumFromOutgoings()+ "zł");
    }

    private String calculateTotalSumFromOutgoings() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getMoneyFromTrade();
        Date calendar = new Date();
        int currentYear=calendar.getYear()+1900;
        int year=0;
        String date="";
        double moneyFromOutgoing=0;
        k=dbHelper.getMoneyFromOutgoings();
        while (k.moveToNext())
        {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            year=getYear(date);
            if(currentYear==year)
                moneyFromOutgoing=moneyFromOutgoing+k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
        }
        return String.format("%.2f", Math.round(moneyFromOutgoing * 100.0) / 100.0);
    }

    private String calculateSpecificOutgoing(String category) {
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
        String stringTotalMoneyFromOutgoing=String.format("%.2f", Math.round(totalMoney * 100.0) / 100.0);

        return stringTotalMoneyFromOutgoing;
    }

    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
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
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_income_daily));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        highgroves=view.findViewById(R.id.highroves);
        foils=view.findViewById(R.id.foils);
        water=view.findViewById(R.id.water);
        sticks=view.findViewById(R.id.sticks);
        seeds=view.findViewById(R.id.seeds);
        plants=view.findViewById(R.id.plants);
        pesticides=view.findViewById(R.id.pesticides);
        fertilizers=view.findViewById(R.id.fertilizers);
        machines=view.findViewById(R.id.machines);
        tools=view.findViewById(R.id.tools);
        others=view.findViewById(R.id.others);
        totalSum=view.findViewById(R.id.total_sum);
        btnOutgoingInChart=view.findViewById(R.id.btn_outgoings_in_chart);
    }

    private void createListeners() {
        btnOutgoingInChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new OutgoingsChartFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

    }

}


