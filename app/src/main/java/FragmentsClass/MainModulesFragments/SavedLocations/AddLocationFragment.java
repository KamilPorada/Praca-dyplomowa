package FragmentsClass.MainModulesFragments.SavedLocations;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.textfield.TextInputEditText;

import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class AddLocationFragment extends Fragment {

    private Context context;
    private TextInputEditText howLocation;
    private ImageView image, buttonComeBack;
    private TextView howCoordinate;
    private Button buttonSave;

    private boolean readCoordinate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_location_fragment, container, false);
        assert container != null;
        context = container.getContext();
        findViews(view);
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
        int id = item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_calculators));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howLocation = view.findViewById(R.id.location);
        image = view.findViewById(R.id.image);
        howCoordinate = view.findViewById(R.id.how_coordinate);
        buttonSave = view.findViewById(R.id.button_save);
        buttonComeBack = view.findViewById(R.id.button_come_back);
    }

    private void createListeners() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        buttonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedLocationsFragment()).commit();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                LocationListener listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
//                        howCoordinate.setText(String.valueOf(location.getLatitude()) + "   " + location.getLongitude());
                        double x = location.getLatitude();
                        double y = location.getLongitude();
                        //1° = 60`
                        //różnica = x`



                        howCoordinate.setText(ToolClass.generateStringCoordinate(x) + "N\n" + ToolClass.generateStringCoordinate(y) + "E");
                        System.out.println(Math.round(Math.abs(100*(Math.abs(60*(x-(Math.round(x)-1))-1)-Math.abs(Math.round(60*(x-(Math.round(x)-1)))-1)))));
                        readCoordinate = true;
                    }
                };
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
            }
        });
    }

    private void validateData() {
        ShowToast toast = new ShowToast();
        if(howLocation.getText().toString().compareTo("")==0)
            toast.showErrorToast(context,"BŁĄD DANYCH!\n  Uzupełnij nazwę lokalizacji!", R.drawable.icon_information);
        else{
            if(!readCoordinate)
                toast.showErrorToast(context,"BŁĄD DANYCH!\n  Odczytaj współrzędne lokalizacji!", R.drawable.icon_information);
            else
                addToDatabase();
        }

    }

    private void addToDatabase() {
    }
}


