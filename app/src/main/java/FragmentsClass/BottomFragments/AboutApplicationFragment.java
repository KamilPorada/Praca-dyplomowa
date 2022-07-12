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

}
