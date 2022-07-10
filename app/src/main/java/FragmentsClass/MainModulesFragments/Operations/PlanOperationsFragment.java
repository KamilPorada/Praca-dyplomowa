package FragmentsClass.MainModulesFragments.Operations;

import static HelperClasses.ToolClass.getActualYear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import HelperClasses.InformationDialog;
import HelperClasses.ShowAttention;
import HelperClasses.ToolClass;

public class PlanOperationsFragment extends Fragment {


    private Fragment fragment = null;
    private Context context;

    private RadioButton insecticidies, fungicidies, herbicidies;
    private TextInputEditText howDate, howHour;
    private TextView howAge, howPesticide, titleHighgroves;
    private SeekBar howHighgroves;
    private ImageButton editDateButton, editHourButton, addPesticideButton;
    private Button planOperationButton, cancelButton;

    private String date, hour, pesticides, typeOfPesticides;
    private int highgroves, days;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_plan_operations_fragment, container, false);
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculators));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        insecticidies=view.findViewById(R.id.insecticidies);
        fungicidies=view.findViewById(R.id.fungicidies);
        herbicidies=view.findViewById(R.id.herbicidies);
        howDate=view.findViewById(R.id.date);
        howHour=view.findViewById(R.id.hour);
        howAge=view.findViewById(R.id.how_age);
        howPesticide=view.findViewById(R.id.how_pesticides);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        titleHighgroves=view.findViewById(R.id.title_highgroves);
        editDateButton=view.findViewById(R.id.edit_date_button);
        editHourButton=view.findViewById(R.id.edit_hour_button);
        addPesticideButton=view.findViewById(R.id.add_pesticide_button);
        planOperationButton=view.findViewById(R.id.button_plan_operations);
        cancelButton=view.findViewById(R.id.button_cancel);
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.edit_date_button:
                    {

                    }break;
                    case R.id.edit_hour_button:
                    {

                    }break;
                    case R.id.add_pesticide_button:
                    {

                    }break;
                    case R.id.button_plan_operations:
                    {
                        validateData();
                    }break;
                    case R.id.button_cancel:
                    {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OperationsFragment()).commit();
                    }break;
                }
            }
        };
        editDateButton.setOnClickListener(listener);
        editHourButton.setOnClickListener(listener);
        addPesticideButton.setOnClickListener(listener);
        planOperationButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);

        howHighgroves.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                titleHighgroves.setText("Ilość tuneli do opryskania: " + progress);
                highgroves=progress;
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
        ShowAttention showAttention = new ShowAttention();
        boolean checkRadaio = insecticidies.isChecked() || fungicidies.isChecked() || herbicidies.isChecked();
        boolean checkDate;
        boolean checkHour;
        boolean checkPesticide;

        date=howDate.getText().toString();
        hour=howHour.getText().toString();
        pesticides=howPesticide.getText().toString();

        if(!checkRadaio)
            showAttention.showToast(R.layout.toast_layout,null, requireActivity(),context,"Wybierz rodzaj pestycydu!");

        if(ToolClass.checkValidateData(howDate.getText().toString()))
            if(ToolClass.checkValidateYear(howDate.getText().toString()))
                checkDate=true;
            else {
                showAttention.showToast(R.layout.toast_layout,null, requireActivity(),context,"Podaj poprawny rok!\nMamy aktualnie "+getActualYear()+" rok!");
                checkDate = false;
            }
        else {
            showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Zły format daty!\n[dd.mm.rrrr]");
            checkDate = false;
        }

        if(ToolClass.checkValidateHour(hour))
            checkHour=true;
        else{
            showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Zły format godziny!\n[gg:mm]");
            checkHour = false;
        }

        if(pesticides.compareTo("")==0){
            checkPesticide=false;
            showAttention.showToast(R.layout.toast_layout, null, requireActivity(), context, "Wybierz pestycyd!");
        }
        else
            checkPesticide=true;

        if(checkRadaio && checkDate && checkHour && checkPesticide)
            addOperationsToDataBase();
    }

    private void addOperationsToDataBase() {
    }
}


