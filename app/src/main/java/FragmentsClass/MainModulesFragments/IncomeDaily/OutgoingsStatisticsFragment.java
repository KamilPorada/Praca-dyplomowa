package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
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

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class OutgoingsStatisticsFragment extends Fragment {

    private Fragment fragment = null;
    private Context context;

    private TextView highgroves, foils, water, sticks, seeds, plants, pesticides, fertilizers, machines, tools,
                     others, totalSum;
    private Button btnOutgoingInChart;
    private ImageView buttonComeBack;

    private final String [] categories = {
            "Konstrukcje tuneli", "Folie ogrodnicze", "Hydraulika w tunelach",
            "Paliki do tuneli", "Nasiona papryki", "Sadzonki papryki", "Pestycydy", "Nawozy",
            "Maszyny rolnicze", "Narzędzia ogrodnicze", "Inne"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_statistics_of_outgoings, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_outgoings_statistics));
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
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        highgroves.setText(StatisticsHelper.calculateSpecificOutgoing(context, categories[0]) + "zł");
        foils.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[1]) + "zł");
        water.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[2]) + "zł");
        sticks.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[3]) + "zł");
        seeds.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[4]) + "zł");
        plants.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[5]) + "zł");
        pesticides.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[6]) + "zł");
        fertilizers.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[7]) + "zł");
        machines.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[8]) + "zł");
        tools.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[9]) + "zł");
        others.setText(StatisticsHelper.calculateSpecificOutgoing(context,categories[10]) + "zł");
        totalSum.setText(StatisticsHelper.getMoneyFromOutgoings(context,ToolClass.getActualYear()) + "zł");
    }

    private void createListeners() {
        btnOutgoingInChart.setOnClickListener(v -> {
            fragment=new OutgoingsChartFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        });

        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BalanceFragment()).commit());

    }

}


