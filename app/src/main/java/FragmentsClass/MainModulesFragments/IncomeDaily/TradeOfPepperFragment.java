package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;


public class TradeOfPepperFragment extends Fragment {

    private Context context;
    private final ArrayList<TradePepperItem> TradePepperList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView buttonComeBack, buttonAddItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        View view = inflater.inflate(R.layout.layout_trade_of_pepper,container,false);
        findViews(view);
        startSettings();
        createListener();
        loadData();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_trade_of_pepper));
        }
        return super.onOptionsItemSelected(item);
    }



    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        buttonComeBack=view.findViewById(R.id.button_come_back);
        buttonAddItem=view.findViewById(R.id.button_add_item);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        TradePepperAdapter adapter = new TradePepperAdapter(TradePepperList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TradePepperAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SharedPreferencesNames.ToolSharedPreferences.TRADE_OF_PEPPER_OPEN_MODE, 0);
                editor.putInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_TRADE_RV, TradePepperList.get(position).getIId());
                editor.apply();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddTradeFragment()).commit();
            }

            @Override
            public void onDeleteClick(int position) {
                openDialogQuestion(position);
            }
        });
    }

    private void createListener() {
        buttonComeBack.setOnClickListener( v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new IncomeDailyFragment()).commit());
        buttonAddItem.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SharedPreferencesNames.ToolSharedPreferences.TRADE_OF_PEPPER_OPEN_MODE, 1);
            editor.apply();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddTradeFragment()).commit();
        });
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getTradeOfPepperItems();
        while(k.moveToNext())
        {
            int id = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem._ID));
            int color=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_COLOR_OF_PEPPER));
            int clas=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_CLASS_OF_PEPPER));
            double price=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PRICE_OF_PEPPER));
            double weight=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_WEIGHT_OF_PEPPER));
            double sum=k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_TOTAL_SUM));
            String date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_DATE));
            String place=k.getString(k.getColumnIndexOrThrow(DataBaseNames.TradeOfPepperItem.COLUMN_PLACE));

            @SuppressLint("DefaultLocale") String totalSum=String.format("%.2f", Math.round(sum * 100.0) / 100.0);

            if(ToolClass.getActualYear()==ToolClass.getYear(date))
                TradePepperList.add(new TradePepperItem(id,color,date,clas,price,weight,totalSum, place));
        }
    }


    private void openDialogQuestion(int position) {
        Dialog questionWindow = new Dialog(context);
        questionWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        questionWindow.setContentView(R.layout.dialog_question);
        questionWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        questionWindow.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        questionWindow.show();
        createAndAddListener(questionWindow, position);
    }


    private void createAndAddListener(Dialog questionWindow, int position) {
        Button cancelButton = questionWindow.findViewById(R.id.cancel_button);
        Button deleteButton = questionWindow.findViewById(R.id.delete_button);
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.cancel_button:
                {
                    questionWindow.dismiss();
                }break;
                case R.id.delete_button:
                {
                    deleteItem(position);
                    questionWindow.dismiss();
                }break;
            }
        };
        cancelButton.setOnClickListener(listener);
        deleteButton.setOnClickListener(listener);
    }

    private void deleteItem(int position) {
        DataBaseHelper db = new DataBaseHelper(context);
        db.deleteItem(DataBaseNames.TradeOfPepperItem.TABLE_NAME,TradePepperList.get(position).getIId());
        ShowToast toast = new ShowToast();
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie usunąłeś transakcję!");
        Fragment fragment = new TradeOfPepperFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }
}
