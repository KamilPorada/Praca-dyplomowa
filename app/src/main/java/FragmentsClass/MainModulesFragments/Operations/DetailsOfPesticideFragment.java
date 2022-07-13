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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses.TradePepperItem;
import HelperClasses.InformationDialog;
import HelperClasses.ToolClass;

public class DetailsOfPesticideFragment extends Fragment {


    private Fragment fragment = null;
    private Context context;

    private TextView typeOfPesticide, nameOfPesticide;
    private ImageView imageOfPesticide;

    private String sentName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_details_of_pesticide, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
        loadData();
        return view;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PESTICIDES_CHOSEN",Context.MODE_PRIVATE);
        sentName = sharedPreferences.getString("CHOSEN", "ABAMAX 018 EC");

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getSpecifyPesticideValues(sentName);
        String nameOfP, notesOfP;
        int typeOfP=0, typeOfD, graceOfP, imageOfP = 0;
        double doseOfP;
        while(k.moveToNext())
        {
            nameOfP = k.getString(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST));
            typeOfP = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE));
            doseOfP = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_DOSE));
            typeOfD = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE));
            graceOfP = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_OF_GRACE));
            notesOfP = k.getString(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NOTES));
            imageOfP = k.getInt(k.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_IMAGE));
        }
        
        if(typeOfP==0)
            typeOfPesticide.setText("INSEKTYCYD");
        else if(typeOfP==1)
            typeOfPesticide.setText("FUNGICYD");
        else if(typeOfP==2)
            typeOfPesticide.setText("HERBICYD");

        nameOfPesticide.setText(sentName);
        imageOfPesticide.setImageResource(imageOfP);
        System.out.println(imageOfP);
            
        
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
        typeOfPesticide=view.findViewById(R.id.type_of_pesticide);
        nameOfPesticide=view.findViewById(R.id.name_of_pesticide);
        imageOfPesticide=view.findViewById(R.id.image_of_pesticide);
    }

    private void createListeners() {

    }
}



