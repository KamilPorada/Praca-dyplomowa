package FragmentsClass.MainModulesFragments.Operations;

import static HelperClasses.ToolClass.getActualYear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Matrix;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;
import HelperClasses.ShowAttention;
import HelperClasses.ToolClass;

public class DetailsOfOperationFragment extends Fragment {


    private Fragment fragment = null;
    private Context context;

    private ImageView imageOfPest;
    private TextView titleOfOperation, howDate, howTime, howGrace, howDateOfEndOfGrace,
                     howAge, howHighgroves, howPesticide, howFluid, howStatus;
    private Button buttonChangeStatus, buttonInstructions, buttonComeBack;

    private int id=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_details_of_operation, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        loadData();
        createListeners();
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
        imageOfPest=view.findViewById(R.id.image_of_pest);
        titleOfOperation=view.findViewById(R.id.title_of_operation);
        howDate=view.findViewById(R.id.how_date);
        howTime=view.findViewById(R.id.how_time);
        howGrace=view.findViewById(R.id.how_grace);
        howDateOfEndOfGrace=view.findViewById(R.id.how_date_of_end_of_grace);
        howAge=view.findViewById(R.id.how_age);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        howPesticide=view.findViewById(R.id.how_pesticide);
        howFluid=view.findViewById(R.id.how_fluid);
        howStatus=view.findViewById(R.id.how_status);
        buttonChangeStatus=view.findViewById(R.id.button_change_status);
        buttonInstructions=view.findViewById(R.id.button_instructions);
        buttonComeBack=view.findViewById(R.id.button_come_back);
    }


    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.button_change_status:
                    {
                        updateOperationStatus();
                    }break;
                    case R.id.button_instructions:
                    {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InstructionOfOperationFragment()).commit();
                    }break;
                    case R.id.button_come_back:
                    {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatalogOfOperationsFragment()).commit();
                    }break;
                }
            }
        };
        buttonChangeStatus.setOnClickListener(listener);
        buttonInstructions.setOnClickListener(listener);
        buttonComeBack.setOnClickListener(listener);
    }

    private void updateOperationStatus() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        dataBaseHelper.updateOperationStatus(id,1);
        imageOfPest.animate().setDuration(2000).scaleYBy(0.15f).scaleXBy(0.15f);
        imageOfPest.animate().rotationYBy(360).setDuration(1000);
        buttonChangeStatus.setVisibility(View.INVISIBLE);
        howStatus.setText("Wykonano");
    }

    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("POSITION_OF_OPERATION_RV", 0);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        Cursor k1=dataBaseHelper.getSpecifyOperationsValues(id);
        while (k1.moveToNext())
        {
            howDate.setText(k1.getString(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_DATE)));
            howTime.setText(k1.getString(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_TIME)));
            howDateOfEndOfGrace.setText(k1.getString(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_DATE_END_OF_GRACE)));
            howHighgroves.setText(String.valueOf(k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_HIGHGROVES))));
            howFluid.setText(k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_FLUID))+" litrów");
            int age = k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_AGE_OF_PEPPER));
            int status = k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_STATUS));
            int idOfPesticide = k1.getInt(k1.getColumnIndexOrThrow(DataBaseNames.OperationsItem.COLUMN_ID_PESTICIDE));
            if (status==0)
                howStatus.setText("Zaplanowano");
            else
            {
                howStatus.setText("Wykonano");
                buttonChangeStatus.setVisibility(View.INVISIBLE);
            }

            Cursor k2 = dataBaseHelper.getSpecifyPesticideValues(idOfPesticide);
            while (k2.moveToNext())
            {
                int grace = k2.getInt(k2.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_OF_GRACE));
                howPesticide.setText(k2.getString(k2.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES)));
                int typeOfPesticide=k2.getInt(k2.getColumnIndexOrThrow(DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE));
                switch (typeOfPesticide)
                {
                    case 0:
                    {
                        titleOfOperation.setText("Zabieg\nrobakobójczy");
                        imageOfPest.setImageResource(R.drawable.image_worm);
                    }break;
                    case 1:
                    {
                        titleOfOperation.setText("Zabieg\ngrzybobójczy");
                        imageOfPest.setImageResource(R.drawable.image_mushrooms);
                    }break;
                    case 2:
                    {
                        titleOfOperation.setText("Zabieg\nchwastobójczy");
                        imageOfPest.setImageResource(R.drawable.image_weed);
                    }break;
                }
                if (typeOfPesticide==2)
                    howAge.setText("-");
                else
                    howAge.setText(String.valueOf(age));
                if(grace==1)
                    howGrace.setText(grace+" dzień");
                else
                    howGrace.setText(grace+" dni");
            }
        }
//        ShowAttention showAttention = new ShowAttention();
//        showAttention.showToast(R.layout.toast_layout,null, requireActivity(),context,date);

    }
}


