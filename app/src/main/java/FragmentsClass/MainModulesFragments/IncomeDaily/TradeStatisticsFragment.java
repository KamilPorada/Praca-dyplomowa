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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class TradeStatisticsFragment extends Fragment {

    private Fragment fragment = null;
    private Context context;
    
    private TextView weightOfRedPepper, weightOfYellowPepper, weightOfGreenPepper, weightOfOrangePepper, 
             weightOfBlondPepper, weightOfFirstClassPepper, weightOfSecondClassPepper,
             weightOfCuttingClassPepper, weightFromHighgrove, incomeFromHighgrove,
             totalSumOfWeight, totalSumOfIncome, averagePriceOfPepper;
    private Button btnTradeInCharts;
    private ImageView buttonComeBack;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_statistics_of_trade, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
        loadData();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_trade_statistics));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        weightOfRedPepper=view.findViewById(R.id.weight_of_red_pepper);
        weightOfYellowPepper=view.findViewById(R.id.weight_of_yellow_pepper);
        weightOfGreenPepper=view.findViewById(R.id.weight_of_green_pepper);
        weightOfOrangePepper=view.findViewById(R.id.weight_of_orange_pepper);
        weightOfBlondPepper=view.findViewById(R.id.weight_of_blond_pepper);
        weightOfFirstClassPepper=view.findViewById(R.id.weight_of_first_class_pepper);
        weightOfSecondClassPepper=view.findViewById(R.id.weight_of_second_class_pepper);
        weightOfCuttingClassPepper=view.findViewById(R.id.weight_of_cutting_class_pepper);
        weightFromHighgrove=view.findViewById(R.id.weight_from_highgrove);
        incomeFromHighgrove=view.findViewById(R.id.income_from_highgrove);
        totalSumOfIncome=view.findViewById(R.id.total_sum_of_income);
        totalSumOfWeight=view.findViewById(R.id.total_sum_of_weight);
        averagePriceOfPepper=view.findViewById(R.id.average_price_of_pepper);
        btnTradeInCharts=view.findViewById(R.id.btn_trade_in_charts);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListeners() {
        btnTradeInCharts.setOnClickListener(v -> {
            fragment = new ColorOfPepperChartFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        });
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BalanceFragment()).commit());

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void loadData() {
        weightOfRedPepper.setText(StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),0) + " kg");
        weightOfYellowPepper.setText(StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),1) + " kg");
        weightOfGreenPepper.setText(StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),2) + " kg");
        weightOfOrangePepper.setText(StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),3) + " kg");
        weightOfBlondPepper.setText(StatisticsHelper.calculateWeightFromColorOfPepper(context, ToolClass.getActualYear(),4) + " kg");
        weightOfFirstClassPepper.setText(StatisticsHelper.calculateWeightFromClassOfPepper(context, ToolClass.getActualYear(),1) + " kg");
        weightOfSecondClassPepper.setText(StatisticsHelper.calculateWeightFromClassOfPepper(context, ToolClass.getActualYear(),2) + " kg");
        weightOfCuttingClassPepper.setText(StatisticsHelper.calculateWeightFromClassOfPepper(context, ToolClass.getActualYear(),3) + " kg");
        weightFromHighgrove.setText(String.format("%.2f", Math.round(StatisticsHelper.calculateWeightFromHighgrove(context,ToolClass.getActualYear(),ToolClass.getHighgroves(context)) * 100.0) / 100.0) + " kg");
        incomeFromHighgrove.setText(String.format("%.2f", Math.round(StatisticsHelper.calculateIncomeFromHighgrove(context,ToolClass.getActualYear(),ToolClass.getHighgroves(context)) * 100.0) / 100.0) + " zł");
        totalSumOfWeight.setText(String.format("%.2f", Math.round(StatisticsHelper.calculateWeightFromHighgrove(context,ToolClass.getActualYear(),1) * 100.0) / 100.0) + " kg");
        totalSumOfIncome.setText(String.format("%.2f", Math.round(StatisticsHelper.calculateIncomeFromHighgrove(context,ToolClass.getActualYear(),1) * 100.0) / 100.0) + " zł");
        averagePriceOfPepper.setText(String.format("%.2f", Math.round((StatisticsHelper.getAveragePriceOfPepper(context)) * 100.0) / 100.0) + " zł");
    }
}


