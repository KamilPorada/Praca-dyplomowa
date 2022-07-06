package FragmentsClass.BottomFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import FragmentsClass.MainModulesFragments.Calculators.CalculatorsFragment;
import FragmentsClass.MainModulesFragments.IncomeDaily.IncomeDailyFragment;
import OthersClass.DarkMode;
import OthersClass.InformationDialog;

public class HomeFragment extends Fragment {

    Context context;

    ConstraintLayout btnCalculator, btnIncomeDaily, btnOperations,
            btnNotes, btnImportantPlaces, btnClock;

    Fragment fragment = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment, container, false);
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
                    case R.id.btn_calculator:
                    {
                        fragment = new CalculatorsFragment();
                    }break;
                    case R.id.btn_income_daily:
                    {
                        fragment = new IncomeDailyFragment();
                    }break;
                    case R.id.btn_operations:
                    {

                    }break;
                    case R.id.btn_notes:
                    {

                    }break;
                    case R.id.btn_important_places:
                    {

                    }break;
                    case R.id.btn_clock:
                    {

                    }break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        };
        btnCalculator.setOnClickListener(listener);
        btnIncomeDaily.setOnClickListener(listener);
        btnOperations.setOnClickListener(listener);
        btnNotes.setOnClickListener(listener);
        btnImportantPlaces.setOnClickListener(listener);
        btnClock.setOnClickListener(listener);
    }

    private void findViews(View view) {
        btnCalculator=view.findViewById(R.id.btn_calculator);
        btnIncomeDaily=view.findViewById(R.id.btn_income_daily);
        btnOperations=view.findViewById(R.id.btn_operations);
        btnNotes=view.findViewById(R.id.btn_notes);
        btnImportantPlaces=view.findViewById(R.id.btn_important_places);
        btnClock=view.findViewById(R.id.btn_clock);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.change_mode:
            {
                DarkMode darkMode = new DarkMode();
                darkMode.updateMode();
            }break;
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_home));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
}

  
