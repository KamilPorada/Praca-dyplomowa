package FragmentsClass.MainModulesFragments.Operations;

import static HelperClasses.ToolClass.getActualYear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperItem;
import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfPesticideAdapter;
import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfPesticideItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowAttention;
import HelperClasses.ToolClass;

public class CatalogOfPesticidesFragment extends Fragment {


    private Fragment fragment = null;
    private Context context;
    private final ArrayList<CatalogOfPesticideItem> catalogOfPesticideItems = new ArrayList<>();

    private ImageView image;
    private RadioGroup pesticideGroup;
    private RadioButton insecticides, fungicides, herbicides;
    private RecyclerView recyclerView;

    private int [] images = {
            R.drawable.image_worm, R.drawable.image_mushrooms, R.drawable.image_weed
    };
    private int typeOfPesticides;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_catalog_of_pesticides_fragment, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
        startSettings();
        return view;
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getCatalogOfPesticidesNames();
        SharedPreferences sharedPreferences = context.getSharedPreferences("RADIO_BUTTON_PESTICIDES",Context.MODE_PRIVATE);
        typeOfPesticides = sharedPreferences.getInt("SELECTED", 0);

        if(typeOfPesticides==0)
            insecticides.setChecked(true);
        else if(typeOfPesticides==1)
            fungicides.setChecked(true);
        else if(typeOfPesticides==2)
            herbicides.setChecked(true);
        image.setImageResource(images[typeOfPesticides]);

        while(k.moveToNext())
        {
            String name=k.getString(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES));
            int type=k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE));
            if(type==0 && typeOfPesticides==0)
                catalogOfPesticideItems.add(new CatalogOfPesticideItem(name));
            else if(type==1 && typeOfPesticides==1)
                catalogOfPesticideItems.add(new CatalogOfPesticideItem(name));
            else if(type==2 && typeOfPesticides==2)
                catalogOfPesticideItems.add(new CatalogOfPesticideItem(name));
        }
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        CatalogOfPesticideAdapter adapter = new CatalogOfPesticideAdapter(catalogOfPesticideItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatalogOfPesticideAdapter.OnItemClickListener() {
            @Override
            public void onShowInformation(int position) {

            }
        });
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
        pesticideGroup=view.findViewById(R.id.pesticides_group);
        insecticides=view.findViewById(R.id.insecticidies);
        fungicides=view.findViewById(R.id.fungicidies);
        herbicides=view.findViewById(R.id.herbicidies);
        recyclerView=view.findViewById(R.id.recycler_view);
        image=view.findViewById(R.id.image);
    }

    private void createListeners() {
        pesticideGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("RADIO_BUTTON_PESTICIDES",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (checkedId) {
                    case R.id.insecticidies: {
                        typeOfPesticides = 0;
                        image.setImageResource(images[typeOfPesticides]);
                        editor.putInt("SELECTED", 0);
                        editor.apply();
                        insecticides.setChecked(true);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfPesticidesFragment()).commit();
                    }
                    break;
                    case R.id.fungicidies: {
                        typeOfPesticides = 1;
                        image.setImageResource(images[typeOfPesticides]);
                        editor.putInt("SELECTED", 1);
                        editor.apply();
                        fungicides.setChecked(true);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfPesticidesFragment()).commit();
                    }
                    break;
                    case R.id.herbicidies: {
                        typeOfPesticides = 2;
                        image.setImageResource(images[typeOfPesticides]);
                        editor.putInt("SELECTED", typeOfPesticides);
                        editor.apply();
                        herbicides.setChecked(true);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfPesticidesFragment()).commit();
                    }break;
                }
            }
        });
    }
}


