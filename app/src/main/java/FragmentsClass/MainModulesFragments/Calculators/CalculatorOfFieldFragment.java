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
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;

public class CalculatorOfFieldFragment extends Fragment {

    private Context context;

    private ImageView buttonComeBack;
    private EditText length, width;
    private RadioButton ares, hektares, quadraticMeters, quadraticKilometers;
    private Button buttonCalculateField;
    private TextView answer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_calculator_of_field_fragment, container, false);
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculator_of_field));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        buttonComeBack=view.findViewById(R.id.button_come_back);
        length=view.findViewById(R.id.length);
        width=view.findViewById(R.id.width);
        ares=view.findViewById(R.id.ares);
        hektares=view.findViewById(R.id.hektares);
        quadraticMeters=view.findViewById(R.id.quadratic_meters);
        quadraticKilometers=view.findViewById(R.id.quadratic_kilometers);
        buttonCalculateField=view.findViewById(R.id.calculate_field);
        answer=view.findViewById(R.id.answer);
    }

    private void createListeners() {

        buttonCalculateField.setOnClickListener(v -> validateData());
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CalculatorsFragment()).commit());
    }

    private void validateData() {
        ShowToast showToast = new ShowToast();
        boolean validateLength;
        boolean validateWidth;
        if (String.valueOf(length.getText()).compareTo("")==0)
        {
            showToast.showErrorToast(context,"BŁĄD DANYCH\n"+"  Uzupełnij pole długość działki!", R.drawable.icon_information);
            validateLength=false;
        }
        else validateLength=true;
        if (String.valueOf(width.getText()).compareTo("")==0)
        {
            showToast.showErrorToast(context,"BŁĄD DANYCH\n"+"  Uzupełnij pole szerokość działki!", R.drawable.icon_information);
            validateWidth=false;
        }
        else validateWidth=true;

        if(validateLength && validateWidth)
            calculateAnswer();
    }

    @SuppressLint("SetTextI18n")
    private void calculateAnswer() {
        double l=Double.parseDouble(String.valueOf(length.getText()));
        double w=Double.parseDouble(String.valueOf(width.getText()));
        double an=l*w;
        double copy_an=an;
        String unit="";
        if(ares.isChecked()) {
            an = an/100;
            unit="a";
        }
        if(hektares.isChecked()) {
            an = an/10000;
            unit="ha";
        }
        if(quadraticMeters.isChecked()) {
            unit="m²";
        }
        if(quadraticKilometers.isChecked()) {
            an = an/1000000;
            unit="km²";
        }

        answer.setText(getNormalizedNumber(an, copy_an) + unit);
    }

    @SuppressLint("DefaultLocale")
    private String getNormalizedNumber(double an, double copy_an) {
        if(ares.isChecked())
            return String.format("%.2f", an);
        else if (hektares.isChecked()) {
            if(copy_an>100)
                return String.format("%.3f", Math.round(an * 1000.0) / 1000.0);
            else
                return String.format("%.4f", an);
        }
        else if(quadraticMeters.isChecked())
            return String.format("%.2f", an);
        else
        {
            if(copy_an<100)
                return String.format("%.6f", Math.round(an * 1000000.0) / 1000000.0);
            else if(copy_an>100  && copy_an<1000)
                return String.format("%.5f", Math.round(an * 100000.0) / 100000.0);
            else if(copy_an>1000 && copy_an<10000)
                return String.format("%.4f", Math.round(an * 10000.0) / 10000.0);
            else if(copy_an>10000 && copy_an<100000)
                return String.format("%.3f", Math.round(an * 1000.0) / 1000.0);
            else
                return String.format("%.2f", Math.round(an * 100.0) / 100.0);
        }
    }
}


