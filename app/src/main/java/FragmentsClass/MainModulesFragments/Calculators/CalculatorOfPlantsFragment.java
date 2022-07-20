package FragmentsClass.MainModulesFragments.Calculators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import HelperClasses.InformationDialog;

public class CalculatorOfPlantsFragment extends Fragment {

    private Context context;

    private RadioButton standardHighgroves, biggerHighgroves;
    private SeekBar changeDistance;
    private TextView howDistance, description, finalAnswer;
    private Button calculatePlants;
    private ImageView buttonComeBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_calculator_of_plants_fragment, container, false);
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
        standardHighgroves=view.findViewById(R.id.standard_highgroves);
        biggerHighgroves=view.findViewById(R.id.bigger_highgroves);
        changeDistance=view.findViewById(R.id.change_distance);
        howDistance=view.findViewById(R.id.how_distance);
        description=view.findViewById(R.id.description);
        finalAnswer=view.findViewById(R.id.final_answer);
        calculatePlants=view.findViewById(R.id.calculate_plants);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListeners() {
        calculatePlants.setOnClickListener(v -> calculate());
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CalculatorsFragment()).commit());

        changeDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                howDistance.setText(progress + "cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        double length=3000;
        int dredge=0;
        double plantsPerDredge;
        double plantsPerHighgrove;
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
                "\nIlość redlanek w tunelu foliowym: " + dredge +
                "\nIlość sadzonek w każdej redlance: " + (int) plantsPerDredge +
                "\nIlość sadzonek w tunelu foliowym: " + (int) plantsPerHighgrove);
        finalAnswer.setText(String.valueOf((int)plantsPerHighgrove));
    }
}
