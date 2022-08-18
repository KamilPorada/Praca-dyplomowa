package FragmentsClass.MainModulesFragments.SavedLocations;

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
import DataBase.SharedPreferencesNames;
import FragmentsClass.MainModulesFragments.Notes.AddNoteFragment;
import FragmentsClass.MainModulesFragments.Notes.MyNotesFragment;
import FragmentsClass.MainModulesFragments.SavedLocations.SavedLocationsViewsClasses.LocationAdapter;
import FragmentsClass.MainModulesFragments.SavedLocations.SavedLocationsViewsClasses.LocationItem;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;

public class DailyOfLocationsFragment extends Fragment {

    private Context context;
    private final ArrayList<LocationItem> locationItems = new ArrayList<>();
    private ImageView buttonComeBack;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_daily_of_location_fragment, container, false);
        assert container != null;
        context=container.getContext();
        findViews(view);
        createListeners();
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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculators));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        buttonComeBack = view.findViewById(R.id.button_come_back);
        recyclerView=view.findViewById(R.id.recycler_view);
    }

    private void createListeners() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedLocationsFragment()).commit());
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        LocationAdapter adapter = new LocationAdapter(locationItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_LOCATION_RV, locationItems.get(position).getIId());
                editor.apply();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DetailsOfLocationFragment()).commit();
            }

            @Override
            public void onDeleteClick(int position) {
                openDialogQuestion(position);
            }
        });
    }

    private void loadData()
    {
        DataBaseHelper db = new DataBaseHelper(context);
        Cursor c = db.getLocations();
        while (c.moveToNext())
        {
            int id = c.getInt(c.getColumnIndexOrThrow(DataBaseNames.LocationItem._ID));
            String name = c.getString(c.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION));
            double latitude = c.getDouble(c.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_LATITUDE));
            double longitude = c.getDouble(c.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_LONGITUDE));

            locationItems.add(new LocationItem(id,name,latitude,longitude));
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
        db.deleteItem(DataBaseNames.LocationItem.TABLE_NAME,locationItems.get(position).getIId());
        ShowToast toast = new ShowToast();
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie usunąłeś lokalizację!");
        Fragment fragment = new DailyOfLocationsFragment();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }
}


