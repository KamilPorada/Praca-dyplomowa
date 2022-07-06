package FragmentsClass.MainModulesFragments.Calculators;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import OthersClass.DarkMode;
import OthersClass.InformationDialog;
import OthersClass.ShowAttention;

public class CalculatorOfFieldFragment extends Fragment {

    Context context;

    EditText length, width;
    RadioButton ares, hektares, quadraticMeters, quadraticKilometers;
    Button calculateField, resetData;
    TextView answer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_calculator_of_field_fragment, container, false);
        findViews(view);
        createListeners();
        return view;
    }

    private void createListeners() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.calculate_field:
                    {
                        validateData();
                    }break;
                    case R.id.reset_data:
                    {
                        resetValues();
                    }break;
                }
            }
        };
        calculateField.setOnClickListener(listener);
        resetData.setOnClickListener(listener);
    }

    private void resetValues() {
        length.setText("");
        width.setText("");
        ares.setChecked(false);
        hektares.setChecked(false);
        quadraticMeters.setChecked(false);
        quadraticKilometers.setChecked(false);
        answer.setText("");
    }

    private void validateData() {
        ShowAttention showAttention = new ShowAttention();
        boolean validateLength = true;
        boolean validateWidth = true;
        boolean validateRadioButtons =  true;
        if (String.valueOf(length.getText()).compareTo("")==0)
        {
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Uzupełnij pole długość działki!");
            validateLength=false;
        }
        else validateLength=true;
        if (String.valueOf(width.getText()).compareTo("")==0)
        {
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Uzupełnij pole szerokość działki!");
            validateWidth=false;
        }
        else validateWidth=true;
        if (!ares.isChecked() && !hektares.isChecked() &&
                !quadraticMeters.isChecked() && !quadraticKilometers.isChecked())
        {
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Wybierz jednostkę wyniku!");
            validateRadioButtons=false;
        }
        else validateRadioButtons=true;

        if(validateLength && validateWidth && validateRadioButtons)
        {
            calculateAnswer();
        }

    }

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


    private void findViews(View view) {
        length=view.findViewById(R.id.length);
        width=view.findViewById(R.id.width);
        ares=view.findViewById(R.id.ares);
        hektares=view.findViewById(R.id.hektares);
        quadraticMeters=view.findViewById(R.id.quadratic_meters);
        quadraticKilometers=view.findViewById(R.id.quadratic_kilometers);
        calculateField=view.findViewById(R.id.calculate_field);
        resetData=view.findViewById(R.id.reset_data);
        answer=view.findViewById(R.id.answer);
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


