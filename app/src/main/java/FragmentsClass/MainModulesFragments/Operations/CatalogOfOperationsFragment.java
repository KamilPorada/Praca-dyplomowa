package FragmentsClass.MainModulesFragments.Operations;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;
import java.util.Calendar;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfOperationAdapter;
import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfOperationItem;
import HelperClasses.InformationDialog;
import HelperClasses.ToolClass;

public class CatalogOfOperationsFragment extends Fragment {

    private Context context;
    private final ArrayList<CatalogOfOperationItem> catalogOfOperationItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView buttonComeBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_catalog_of_operations, container, false);
        assert container != null;
        context=container.getContext();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculators));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }
    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        CatalogOfOperationAdapter adapter = new CatalogOfOperationAdapter(catalogOfOperationItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("POSITION_OF_OPERATION_RV", catalogOfOperationItems.get(position).getId());
            editor.apply();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DetailsOfOperationFragment()).commit();
        });
    }

    private void createListener() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OperationsFragment()).commit());
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k1 = dbHelper.getOperationCatalogData();
        Cursor k2;
        String date, time, stringStatus="", pesticide="", graceString="", describe="", endOgGrace;
        int grace, idOfPesticide, idOfOperation, typeOfPesticides, image=0, status;
        while(k1.moveToNext()) {
            date = k1.getString(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_DATE));
            time = k1.getString(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_TIME));
            status = k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_STATUS));
            endOgGrace=k1.getString(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE));
            idOfPesticide = k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE));
            idOfOperation = k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem._ID));

            k2 = dbHelper.getSpecifyPesticideValues(idOfPesticide);
            while (k2.moveToNext()) {
                pesticide = k2.getString(k2.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES));
                grace = k2.getInt(k2.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_OF_GRACE));
                typeOfPesticides = k2.getInt(k2.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE));
                if (grace==1)
                    graceString = grace + " dzień";
                else
                    graceString = grace + " dni";

                if (typeOfPesticides==0)
                {
                    describe = "Zabieg robakobójczy";
                    image = R.drawable.image_worm;
                }
                else if (typeOfPesticides==1)
                {
                    describe = "Zabieg grzybobójczy";
                    image = R.drawable.image_mushrooms;
                }
                else if(typeOfPesticides==2)
                {
                    describe = "Zabieg chwastobójczy";
                    image = R.drawable.image_weed;
                }
                if(status==0)
                    stringStatus="Zaplanowano";
                else
                    stringStatus="Wykonano";
            }
            //Jeśli data zabiegu mineła i zabieg nie został wykonany usuwamy go, w przeciwnym razie
            //jeżeli rok wykonania zabiegu jest taki sam jak obecny -> dodajemy do widoku
            if(ToolClass.generateCalendarDate(date).before(ToolClass.generateCurrentCalendarDate()) && status==0)
                dbHelper.deleteItem(DataBaseNames.OperationsItem.TABLE_NAME,idOfOperation);
            else
                if(ToolClass.getYear(date)==ToolClass.getActualYear())
                    catalogOfOperationItems.add(new CatalogOfOperationItem(idOfOperation, date, time, stringStatus, graceString, pesticide, endOgGrace, image, describe));
        }
    }

}


