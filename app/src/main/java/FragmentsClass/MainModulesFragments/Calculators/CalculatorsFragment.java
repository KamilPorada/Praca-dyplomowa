package FragmentsClass.MainModulesFragments.Calculators;

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

import OthersClass.InformationDialog;

public class CalculatorsFragment extends Fragment {

    ConstraintLayout btnCalculatorOfField, getBtnCalculatorOfConcentraction, btnCalculatorOfPlants;

    Fragment fragment = null;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_calculators_fragment, container, false);
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
                    case R.id.btn_calculator_of_field:
                    {
                        fragment = new CalculatorOfFieldFragment();
                    }break;
                    case R.id. btn_calculator_of_concentration:
                    {

                    }break;
                    case R.id.btn_calculator_of_plants:
                    {
                        fragment=new CalculatorOfPlantsFragment();
                    }break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        };
        btnCalculatorOfField.setOnClickListener(listener);
        getBtnCalculatorOfConcentraction.setOnClickListener(listener);
        btnCalculatorOfPlants.setOnClickListener(listener);
    }

    private void findViews(View view) {
        btnCalculatorOfField=view.findViewById(R.id.btn_calculator_of_field);
        getBtnCalculatorOfConcentraction=view.findViewById(R.id.btn_calculator_of_concentration);
        btnCalculatorOfPlants=view.findViewById(R.id.btn_calculator_of_plants);
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
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_calculators));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

}


