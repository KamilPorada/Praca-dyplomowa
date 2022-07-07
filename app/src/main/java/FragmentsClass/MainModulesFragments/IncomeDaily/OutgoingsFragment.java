package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
import HelperClasses.ToolClass;

public class OutgoingsFragment extends Fragment {
    
    private Context context;
    private final ArrayList<OutgoingsItem> OutgoingsList = new ArrayList<>();
    private RecyclerView recyclerView;

    private Button canButton, delButton;

    private final String FLAG = "flag";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_outgoings, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tollbar_menu_add_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_outgoings));
            }break;
            case R.id.add_item:
            {
                Bundle bundle = new Bundle();
                bundle.putBoolean(FLAG, false);
                AddOutgoingsFragment fragment = new AddOutgoingsFragment();
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
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
                sendData(position);
            }

            @Override
            public void onDeleteClick(int position) { openDialogQuestion(position);
            }
        });
    }

    private void sendData(int position) {
        Bundle bundle = new Bundle();
        String CATEGORY = "category";
        String PRICE = "price";
        String DATE = "date";
        String DESCRIBE = "describe";
        String PASSSWORD_KEY = "passwordKey";
        bundle.putBoolean(FLAG, true);
        bundle.putString(CATEGORY,OutgoingsList.get(position).getIOutgoingCategory());
        bundle.putDouble(PRICE,OutgoingsList.get(position).getIOutgoingPrice());
        bundle.putString(DATE,OutgoingsList.get(position).getIOutgoingDate());
        bundle.putString(DESCRIBE,OutgoingsList.get(position).getIOutgoingDescribe());
        bundle.putString(PASSSWORD_KEY,OutgoingsList.get(position).getIOutgoingPasswordKey());
        AddOutgoingsFragment fragment = new AddOutgoingsFragment();
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getOutgoingItems();
        int image;
        String date, category, describe, passwordKey;
        double price;
        while(k.moveToNext()) {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            category=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING));
            price = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
            describe = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING));
            passwordKey = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD));
            image = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_IMAGE));

            if(ToolClass.getActualYear()==ToolClass.getYear(date))
                OutgoingsList.add(new OutgoingsItem(image, category, describe, price, date, passwordKey));
        }
    }

    // ------------------------DELETE ITEM WINDOW EVENTS---------------------------//

    private void openDialogQuestion(int position) {
        Dialog questionWindow = new Dialog(context);
        questionWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        questionWindow.setContentView(R.layout.dialog_question);
        questionWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        questionWindow.show();
        findQuestionDialogViews(questionWindow);
        createAndAddListener(questionWindow, position);
    }

    private void findQuestionDialogViews(Dialog questionWindow) {
        canButton=questionWindow.findViewById(R.id.cancel_button);
        delButton=questionWindow.findViewById(R.id.delete_button);
    }

    private void createAndAddListener(Dialog questionWindow, int position) {
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
        canButton.setOnClickListener(listener);
        delButton.setOnClickListener(listener);
    }

    private void deleteItem(int position) {
        DataBaseHelper db = new DataBaseHelper(context);
        Cursor cursor = db.getItemID(DataBaseNames.OutgoingsItem.TABLE_NAME,DataBaseNames.OutgoingsItem._ID,
                DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD,OutgoingsList.get(position).getIOutgoingPasswordKey());
        int id=0;
        while (cursor.moveToNext())
        {
            id=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem._ID));
        }
        db.deleteItem(DataBaseNames.OutgoingsItem.TABLE_NAME,id);
        Fragment fragment = new OutgoingsFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }
}


