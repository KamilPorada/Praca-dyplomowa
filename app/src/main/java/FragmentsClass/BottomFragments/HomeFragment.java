package FragmentsClass.BottomFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import FragmentsClass.MainModulesFragments.Calculators.CalculatorsFragment;
import FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.IncomeDailyFragment;
import FragmentsClass.MainModulesFragments.Notes.NotesFragment;
import FragmentsClass.MainModulesFragments.Operations.OperationsFragment;
import FragmentsClass.MainModulesFragments.SavedLocations.SavedLocationsFragment;
import HelperClasses.InformationDialog;
import HelperClasses.ToolClass;

public class HomeFragment extends Fragment {

    private Context context;
    private ConstraintLayout btnCalculator, btnIncomeDaily, btnOperations,
            btnNotes, btnImportantPlaces, btnControlOfWater;

    private Fragment fragment = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment, container, false);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toollbar_menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.change_mode:
            {
                ToolClass.updateDarkMode();
            }break;
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_home));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        btnCalculator=view.findViewById(R.id.btn_calculator);
        btnIncomeDaily=view.findViewById(R.id.btn_income_daily);
        btnOperations=view.findViewById(R.id.btn_operations);
        btnNotes=view.findViewById(R.id.btn_notes);
        btnImportantPlaces=view.findViewById(R.id.btn_important_places);
        btnControlOfWater=view.findViewById(R.id.btn_control_of_water);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_calculator:
                {
                    fragment = new CalculatorsFragment();
                }break;
                case R.id.btn_notes:
                {
                    fragment = new NotesFragment();
                }break;
                case R.id.btn_operations:
                {
                    fragment = new OperationsFragment();
                }break;
                case R.id.btn_control_of_water:
                {
                    fragment = new ControlOfWaterFragment();
                }break;
                case R.id.btn_income_daily:
                {
                    fragment = new IncomeDailyFragment();
                }break;
                case R.id.btn_important_places:
                {
                    fragment = new SavedLocationsFragment();
                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnCalculator.setOnClickListener(listener);
        btnIncomeDaily.setOnClickListener(listener);
        btnOperations.setOnClickListener(listener);
        btnNotes.setOnClickListener(listener);
        btnImportantPlaces.setOnClickListener(listener);
        btnControlOfWater.setOnClickListener(listener);
    }
}

  
