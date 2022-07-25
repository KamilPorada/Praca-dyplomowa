package FragmentsClass.MainModulesFragments.Operations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;

public class DetailsOfPesticideFragment extends Fragment {

    private Context context;
    private TextView typeOfPesticide, nameOfPesticide, howPest, howDose, howGrace, howNotes;
    private ImageView imageOfPesticide;
    private Button buttonComeBack;

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
        howPest=view.findViewById(R.id.how_pest);
        howDose=view.findViewById(R.id.how_dose);
        howGrace=view.findViewById(R.id.how_grace);
        howNotes=view.findViewById(R.id.how_notes);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }

    private void createListeners() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfPesticidesFragment()).commit());
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
        sentName = sharedPreferences.getString(SharedPreferencesNames.ToolSharedPreferences.CHOSEN_PESTICIDE, "ABAMAX 018 EC");

        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getSpecifyPesticideValues(sentName);
        String nameOfP = "", notesOfP="";
        int typeOfP=0, typeOfD=0, graceOfP=0, imageOfP = 0;
        double doseOfP=0;
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
        howPest.setText(nameOfP);

        switch (typeOfD)
        {
            case 0:
            {
                howDose.setText(doseOfP+" g/m2");
            }
            case 1:
            {
                howDose.setText(doseOfP+" kg/ha");
            }break;
            case 2:
            {
                howDose.setText(doseOfP+" l/ha");
            }break;
            case 3:
            {
                howDose.setText(doseOfP+" %");
            }break;
            default:
            {
                howDose.setText("Błąd odczytu!");
            }break;
        }

        if(graceOfP==1)
            howGrace.setText(graceOfP + " dzień");
        else
            howGrace.setText(graceOfP + " dni");

        howNotes.setText(notesOfP);
    }
}



