package FragmentsClass.MainModulesFragments.Operations;

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
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class OperationsFragment extends Fragment {

    private ConstraintLayout btnPlanOperations, btnDailyOperations, btnCatalogOfPesticides;
    private Fragment fragment = null;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_operations_fragment, container, false);
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
        btnPlanOperations=view.findViewById(R.id.btn_plan_operations);
        btnDailyOperations=view.findViewById(R.id.btn_daily_operations);
        btnCatalogOfPesticides=view.findViewById(R.id.btn_catalog_of_pesticides);
    }

    private void createListeners() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.btn_plan_operations:
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.BasicData.NAME,Context.MODE_PRIVATE);
                    int flag = sharedPreferences.getInt(SharedPreferencesNames.BasicData.IS_PLANT_EMPTY,0);

                    if(flag==0){
                        ShowToast toast = new ShowToast();
                        toast.showErrorToast(context, "UZUPEŁNIJ POLE DATA ZASADZENIA!\n"+ "  Przejdź -> Notatki gospodarstwa", R.drawable.icon_edit_calendar);
                        fragment = new OperationsFragment();
                    }
                    else {
                        ToolClass.clearTemporaryCurrentOperations(context);
                        fragment = new PlanOperationsFragment();
                    }
                }break;
                case R.id. btn_daily_operations:
                {
                    fragment = new CatalogOfOperationsFragment();
                }break;
                case R.id.btn_catalog_of_pesticides:
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(SharedPreferencesNames.ToolSharedPreferences.CATALOG_OF_PESTICIDE_OPEN_MODE, 1);
                    editor.apply();
                    fragment = new CatalogOfPesticidesFragment();
                }break;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        };
        btnPlanOperations.setOnClickListener(listener);
        btnDailyOperations.setOnClickListener(listener);
        btnCatalogOfPesticides.setOnClickListener(listener);
    }
}


