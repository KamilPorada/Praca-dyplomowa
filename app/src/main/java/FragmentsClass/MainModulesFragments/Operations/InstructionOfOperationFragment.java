package FragmentsClass.MainModulesFragments.Operations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;
import HelperClasses.ToolClass;

public class InstructionOfOperationFragment extends Fragment {

    private Context context;
    private TextView firstSentence, fourthSentence, seventhSentence;
    private Button buttonComeBack;

    private int id=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_instruction_of_operation_fragment, container, false);
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculators));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        firstSentence=view.findViewById(R.id.first_sentence);
        fourthSentence=view.findViewById(R.id.fourth_sentence);
        seventhSentence=view.findViewById(R.id.seventh_sentence);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListeners() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DetailsOfOperationFragment()).commit());
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("POSITION_OF_OPERATION_RV", 0);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        Cursor k = dataBaseHelper.getSpecifyOperationsValues(id);
        int fluid=0, idOfPesticide=0, highgroves=0;
        while (k.moveToNext())
        {
            fluid=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_FLUID));
            idOfPesticide=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE));
            highgroves=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_HIGHGROVES));
        }
        int typeOfDose=0, typeOfPesticide=0;
        double dose=0;
        String pesticide="";
        k=dataBaseHelper.getSpecifyPesticideValues(idOfPesticide);
        while (k.moveToNext())
        {
            typeOfDose=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE));
            typeOfPesticide=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE));
            dose=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_DOSE));
            pesticide=k.getString(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES));
        }
        double amountOfPesticide;

        firstSentence.setText("1.Wlej do opryskiwacza " + Math.round((double) fluid/2) +  " litrów wody.");
        seventhSentence.setText("7.Dolej kolejne " +  Math.round((double) fluid/2) + " litrów wody do opryskiwacza.");

        if(typeOfPesticide==0){
            double doseOfWater = (double) (10000 * fluid) / ToolClass.getHerbicideAreaOfPlantation(highgroves);
            double concentration = dose / (doseOfWater + dose);
            amountOfPesticide = concentration * fluid;
            fourthSentence.setText("4.Wlej do wiaderka z wodą " + String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0) + "l pestycydu " + pesticide + ".");
        }
        else {
            if (typeOfDose == 1) {
                double doseOfWater = (double) (10000 * fluid) / ToolClass.getAreaOfPlantation(highgroves);
                double concentration = dose / (doseOfWater + dose);
                amountOfPesticide = concentration * fluid;
                fourthSentence.setText("4.Wsyp do wiaderka z wodą " + String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0) + "kg pestycydu " + pesticide + ".");
            } else if (typeOfDose == 2) {
                double doseOfWater = (double) (10000 * fluid) / ToolClass.getAreaOfPlantation(highgroves);
                double concentration = dose / (doseOfWater + dose);
                amountOfPesticide = concentration * fluid;
                fourthSentence.setText("4.Wlej do wiaderka z wodą " + String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0) + "l pestycydu " + pesticide + ".");
            } else if (typeOfDose == 3) {
                amountOfPesticide = dose * fluid;
                fourthSentence.setText("4.Wlej do wiaderka z wodą " + String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0) + "l pestycydu " + pesticide + ".");
            }
        }
    }
}


