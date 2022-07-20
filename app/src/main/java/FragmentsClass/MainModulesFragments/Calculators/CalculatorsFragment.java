package FragmentsClass.MainModulesFragments.Calculators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import HelperClasses.InformationDialog;

public class CalculatorsFragment extends Fragment {

    private ConstraintLayout btnCalculatorOfField, getBtnCalculatorOfConcentraction, btnCalculatorOfPlants;

    private Fragment fragment = null;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_calculators_fragment, container, false);
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
        btnCalculatorOfField=view.findViewById(R.id.btn_calculator_of_field);
        getBtnCalculatorOfConcentraction=view.findViewById(R.id.btn_calculator_of_concentration);
        btnCalculatorOfPlants=view.findViewById(R.id.btn_calculator_of_plants);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_calculator_of_field:
                {
                    fragment = new CalculatorOfFieldFragment();
                }break;
                case R.id.btn_calculator_of_plants:
                {
                    fragment=new CalculatorOfPlantsFragment();
                }break;
                case R.id. btn_calculator_of_concentration:
                {
                    fragment=new CalculatorOfConcetrationFragment();
                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnCalculatorOfField.setOnClickListener(listener);
        getBtnCalculatorOfConcentraction.setOnClickListener(listener);
        btnCalculatorOfPlants.setOnClickListener(listener);
    }
}


