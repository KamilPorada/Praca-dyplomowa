package FragmentsClass.MainModulesFragments.SavedLocations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.Operations.CatalogOfOperationsFragment;
import FragmentsClass.MainModulesFragments.Operations.CatalogOfPesticidesFragment;
import FragmentsClass.MainModulesFragments.Operations.PlanOperationsFragment;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class SavedLocationsFragment extends Fragment {

    private ConstraintLayout btnAddLocations, btnDailyOfLocations, btnPolandWithLocations;
    private Fragment fragment = null;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_saved_locations_fragment, container, false);
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
        btnAddLocations=view.findViewById(R.id.btn_add_location);
        btnDailyOfLocations=view.findViewById(R.id.btn_daily_of_locations);
        btnPolandWithLocations=view.findViewById(R.id.btn_poland_with_locations);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_add_location:
                {
                    fragment = new AddLocationFragment();
                }break;
                case R.id. btn_daily_of_locations:
                {
                    fragment = new DailyOfLocationsFragment();
                }break;
                case R.id.btn_poland_with_locations:
                {

                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnAddLocations.setOnClickListener(listener);
        btnDailyOfLocations.setOnClickListener(listener);
        btnPolandWithLocations.setOnClickListener(listener);
    }
}


