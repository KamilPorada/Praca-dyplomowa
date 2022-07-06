package FragmentsClass.MainModulesFragments.Calculators;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import OthersClass.InformationDialog;
import OthersClass.ShowAttention;

public class CalculatorOfPlantsFragment extends Fragment {

    Context context;

    RadioButton standardHighgroves, biggerHighgroves;
    SeekBar changeDistance;
    TextView howDistance, description, finalAnswer;
    Button calculatePlants, resetData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_calculator_of_plants_fragment, container, false);
        context=container.getContext();
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
                    case R.id.calculate_plants:
                    {
                        checkRadioButtons();
                    }break;
                    case R.id.reset_data:
                    {
                        reset();
                    }break;
                }
            }
        };

        calculatePlants.setOnClickListener(listener);
        resetData.setOnClickListener(listener);

        changeDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                howDistance.setText("Odległość sadzonki od sadzonki\n" + progress + "cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void reset() {
        standardHighgroves.setChecked(false);
        biggerHighgroves.setChecked(false);
        description.setText("Rozmiary tunelu foliowego: 0m x 0m\n" +
                "Ilość redlanek w tunelu foliowym: 0\n" +
                "Ilość sadzonek w każdej redlance: 0\n" +
                "Ilość sadzonek w tunelu foliowym: 0");
        finalAnswer.setText("0");
        changeDistance.setProgress(30);
    }

    private void checkRadioButtons() {
        ShowAttention showAttention = new ShowAttention();
        if (!standardHighgroves.isChecked() && !biggerHighgroves.isChecked())
            showAttention.showToast(R.layout.toast_layout,null,getActivity(),context,"Wybierz rozmiar tunelu foliowego!");
        else
            calculate();
    }

    private void calculate() {
        double length=3000;
        int dredge=0;
        double plantsPerDredge=0;
        double plantsPerHighgrove=0;
        int distance=changeDistance.getProgress();
        String sizesHighgroves="";
        if(standardHighgroves.isChecked())
        {
            dredge=10;
            sizesHighgroves="32m x 8m";
        }
        if(biggerHighgroves.isChecked())
        {
            dredge=15;
            sizesHighgroves="32m x 12m";
        }

        plantsPerDredge=Math.round((length/distance) + 1);
        plantsPerHighgrove=plantsPerDredge*dredge;

        description.setText("Rozmiary tunelu foliowego: " + sizesHighgroves +
                "\nIlość redlanek w tunelu foliowym: " + String.valueOf(dredge) +
                "\nIlość sadzonek w każdej redlance: " + String.valueOf((int)plantsPerDredge) +
                "\nIlość sadzonek w tunelu foliowym: " + String.valueOf((int)plantsPerHighgrove));
        finalAnswer.setText(String.valueOf((int)plantsPerHighgrove));
    }


    private void findViews(View view) {
        standardHighgroves=view.findViewById(R.id.standard_highgroves);
        biggerHighgroves=view.findViewById(R.id.bigger_highgroves);
        changeDistance=view.findViewById(R.id.change_distance);
        howDistance=view.findViewById(R.id.how_distance);
        description=view.findViewById(R.id.description);
        finalAnswer=view.findViewById(R.id.final_answer);
        calculatePlants=view.findViewById(R.id.calculate_plants);
        resetData=view.findViewById(R.id.reset_data);
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
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_calculator_of_plants));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
}
