package FragmentsClass.MainModulesFragments.Calculators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputLayout;

import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class CalculatorOfConcetrationFragment extends Fragment {

    private Context context;
    private ImageView buttonComeBack;
    private EditText age, dose;
    private TextInputLayout howDose;
    private SeekBar howHighgroves;
    private TextView titleHighgroves, description, finalAnswer;
    private RadioGroup typeOfDoseGroup;
    private Button buttonCalculate;

    private int typeOfDose=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_calculator_of_concentration, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculator_of_plants));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        buttonComeBack=view.findViewById(R.id.button_come_back);
        age=view.findViewById(R.id.age);
        dose=view.findViewById(R.id.dose);
        howDose=view.findViewById(R.id.how_dose);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        titleHighgroves=view.findViewById(R.id.title_highgroves);
        description=view.findViewById(R.id.description);
        finalAnswer=view.findViewById(R.id.final_answer);
        typeOfDoseGroup=view.findViewById(R.id.type_of_dose_group);
        buttonCalculate=view.findViewById(R.id.button_calculate);

    }

    @SuppressLint("NonConstantResourceId")
    private void createListeners() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CalculatorsFragment()).commit());
        buttonCalculate.setOnClickListener(v -> validateData());

        typeOfDoseGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.liters:
                {
                    typeOfDose=0;
                    howDose.setHint("[l/ha]");
                }break;
                case R.id.kilograms:
                {
                    typeOfDose=1;
                    howDose.setHint("[kg/ha]");
                }break;
                case R.id.percent:
                {
                    typeOfDose=2;
                    howDose.setHint("[%]");
                }break;
            }
        });

        howHighgroves.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                titleHighgroves.setText("Ilość tuneli do opryskania: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        howHighgroves.setMax((int) ToolClass.getHighgroves(context));

    }

    private void validateData() {
        if(age.getText().toString().compareTo("")==0 || dose.getText().toString().compareTo("")==0)
        {
            ShowToast toast = new ShowToast();
            toast.showErrorToast(context,"BŁĄD DANYCH!\n  Uzupełnij wszystkie pola!", R.drawable.icon_information);
        }
        else
            calculateAnswer();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void calculateAnswer() {
        int h=howHighgroves.getProgress();
        int a = Integer.parseInt(age.getText().toString());
        int w;
        double d = Double.parseDouble(dose.getText().toString());
        double doseOfWater, concentration, amountOfPesticide=0;
        if (a < 25)
            w = h * 5;
        else if (a < 50)
            w = h * 10;
        else if (a < 75)
            w = h * 15;
        else
            w = h * 20;

        if (typeOfDose == 0) {
            doseOfWater = (double) (10000 * w) / ToolClass.getAreaOfPlantation(h);
            concentration = d / (doseOfWater + d);
            amountOfPesticide = concentration * w;
            finalAnswer.setText(String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0)+"l");
        }
        else if (typeOfDose == 1) {
            doseOfWater = (double) (10000 * w) / ToolClass.getAreaOfPlantation(h);
            concentration = d / (doseOfWater + d);
            amountOfPesticide = concentration * w;
            finalAnswer.setText(String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0)+"kg");
        }
        else if (typeOfDose==2)
        {
            amountOfPesticide = d * w;
            finalAnswer.setText(String.format("%.2f", Math.round((amountOfPesticide) * 100.0) / 100.0)+"l");
        }

        setData(a,h,w,amountOfPesticide);

    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setData(int a, int h, int w, double ap) {
        String unit = "l";
        String stringDay;
        if(typeOfDose==0)
            unit="l";
        else if(typeOfDose==1)
            unit="kg";
        else if(typeOfDose==2)
            unit="l";

        if (a==1)
            stringDay="dzień";
        else
            stringDay="dni";

        description.setText("Wiek papryki: " + a + " " + stringDay + "\nIlość tuneli: " + h + "\nIlość wody: " + w + "l" +
                            "\nIlość pestycydu: " + String.format("%.2f", Math.round((ap) * 100.0) / 100.0) + unit);
    }
}
