package FragmentsClass.BottomFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import DataBase.DataBaseNames;
import HelperClasses.InformationDialog;

public class AboutApplicationFragment extends Fragment {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        return inflater.inflate(R.layout.layout_about_application_fragment,container,false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        int i=0;
        System.out.println(createInsertQuery("ORTUS 05 SC", "Przędziorek szklarniowiec, szklarniowiec", 0,0.01,3,7,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.", i++));
        System.out.println(createInsertQuery("ORTUS 05 SC", "Przędziorek szklarniowiec, szklarniowiec", 0,0.01,3,7,"Opryskiwać rośliny po zauważeniu szkodnika lub pierwszych objawów uszkodzeń. Jeżeli w poprzednich zesonach nie było problemu ze szkodnikiem, zabieg można ograniczyć do ogniska wystąpienia pierwszych osobników.", i++));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_about_application));
        }
        return super.onOptionsItemSelected(item);
    }

    private static String createInsertQuery(String name, String pest, int typeOfPesticide, double dose, int typeOfDose,
                                            int grace, String notes, int passwordKey) {
        return "INSERT INTO " + DataBaseNames.PesticidesItem.TABLE_NAME + "(" +
                DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PESTICIDES + ", " +
                DataBaseNames.PesticidesItem.COLUMN_NAME_OF_PEST + ", " +
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_PESTICIDE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_DOSE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_TYPE_OF_DOSE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_OF_GRACE + ", " +
                DataBaseNames.PesticidesItem.COLUMN_NOTES + ", " +
                DataBaseNames.PesticidesItem.COLUMN_DATA_PASWORD + ") VALUES (" +
                "'" + name + "'" + ", " + "'" + pest + "'" + ", " + "'" + typeOfPesticide + "'" + ", " + "'" + dose + "'" + ", " +
                "'" + typeOfDose + "'" + ", " + "'" + grace + "'" + ", " + "'" + notes + "'" + ", " + "'" + passwordKey + "'" + ");";
    }
}
