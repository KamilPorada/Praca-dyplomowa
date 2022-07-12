package FragmentsClass.MainModulesFragments.Operations;

import static HelperClasses.ToolClass.getActualYear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import HelperClasses.ToolClass;

public class CatalogOfPesticidesFragment extends Fragment {


    private Fragment fragment = null;
    private Context context;
    private final ArrayList<CatalogOfPesticideItem> catalogOfPesticideItems = new ArrayList<>();

    private ImageView image;
    private RadioGroup pesticideGroup;
    private RecyclerView recyclerView;

    private int [] images = {
            R.drawable.image_worm, R.drawable.image_mushrooms, R.drawable.image_weed
    };
    private int typeOfPesticides=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_catalog_of_pesticides_fragment, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
        startSettings();
        loadData();
        return view;
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getCatalogOfPesticidesNames();
        while(k.moveToNext())
        {
            String name=k.getString(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES));
            catalogOfPesticideItems.add(new CatalogOfPesticideItem(name));
        }

//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("DELAN"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("BI-58"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("FRUCTUS"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("CALIPSO"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("HORUS"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("ACTARAMIC"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("ORKAN"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("ROUNDAUP"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("KARATE"));
//        catalogOfPesticideItems.add(new CatalogOfPesticideItem("SUBSTRAL"));

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
        recyclerView=view.findViewById(R.id.recycler_view);
        image=view.findViewById(R.id.image);
    }

    private void createListeners() {
        pesticideGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.insecticidies: {
                        typeOfPesticides = 0;
                        image.setImageResource(images[typeOfPesticides]);
                    }
                    break;
                    case R.id.fungicidies: {
                        typeOfPesticides = 1;
                        image.setImageResource(images[typeOfPesticides]);
                    }
                    break;
                    case R.id.herbicidies: {
                        typeOfPesticides = 2;
                        image.setImageResource(images[typeOfPesticides]);
                    }
                    break;
                }
            }
        });
        image.setImageResource(images[typeOfPesticides]);
    }
}


