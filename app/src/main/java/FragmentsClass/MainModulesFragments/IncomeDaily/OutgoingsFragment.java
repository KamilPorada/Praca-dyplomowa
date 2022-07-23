package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class OutgoingsFragment extends Fragment {
    
    private Context context;
    private final ArrayList<OutgoingsItem> OutgoingsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView buttonComeBack, buttonAddItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_outgoings, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListener();
        startSettings();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_outgoings));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        buttonComeBack=view.findViewById(R.id.button_come_back);
        buttonAddItem=view.findViewById(R.id.button_add_item);
    }

    private void createListener() {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.button_add_item:
                {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("OUTGOING_OPEN_MODE", 1);
                    editor.apply();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddOutgoingsFragment()).commit();
                }break;
                case R.id.button_come_back:
                {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IncomeDailyFragment()).commit();
                }break;
            }
        };
        buttonAddItem.setOnClickListener(listener);
        buttonComeBack.setOnClickListener(listener);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        OutgoingsAdapter adapter = new OutgoingsAdapter(OutgoingsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OutgoingsAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("OUTGOING_OPEN_MODE", 0);
                editor.putInt("POSITION_OF_OUTGOING_RV", OutgoingsList.get(position).getIId());
                editor.apply();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddOutgoingsFragment()).commit();
            }

            @Override
            public void onDeleteClick(int position) {
                openDialogQuestion(position);
            }
        });
    }


    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getOutgoingItems();
        String date, category, describe;
        int idOfCategory, id;
        double price;
        while(k.moveToNext()) {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            category=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_NAME));
            price = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
            describe = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING));
            idOfCategory = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_ID_OF_CATEGORY));
            id = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem._ID));
            if(ToolClass.getActualYear()==ToolClass.getYear(date))
                OutgoingsList.add(new OutgoingsItem(id,ToolClass.getOutgoingsDrawable(idOfCategory), category, describe, price, date));
        }
    }

    // ------------------------DELETE ITEM WINDOW EVENTS---------------------------//

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
        db.deleteItem(DataBaseNames.OutgoingsItem.TABLE_NAME,OutgoingsList.get(position).getIId());
        ShowToast toast = new ShowToast();
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie usunąłeś wydatek!");
        Fragment fragment = new OutgoingsFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }
}


