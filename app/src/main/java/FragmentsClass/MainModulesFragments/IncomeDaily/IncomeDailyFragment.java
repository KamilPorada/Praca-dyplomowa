package FragmentsClass.MainModulesFragments.IncomeDaily;

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

public class IncomeDailyFragment extends Fragment {

    ConstraintLayout btnTradeOfVegetables, btnOutgoings, btnBalance;

    Fragment fragment = null;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_income_daily_fragment, container, false);
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
                    case R.id.btn_trade_of_vegetables:
                    {
                        fragment = new TradeOfPepperFragment();
                    }break;
                    case R.id. btn_outgoings:
                    {
                        fragment = new OutgoingsFragment();
                    }break;
                    case R.id.btn_balance:
                    {
                        fragment = new BalanceFragment();
                    }break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        };
        btnTradeOfVegetables.setOnClickListener(listener);
        btnOutgoings.setOnClickListener(listener);
        btnBalance.setOnClickListener(listener);
    }

    private void findViews(View view) {
        btnTradeOfVegetables=view.findViewById(R.id.btn_trade_of_vegetables);
        btnOutgoings=view.findViewById(R.id.btn_outgoings);
        btnBalance=view.findViewById(R.id.btn_balance);
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
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_income_daily));
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

}


