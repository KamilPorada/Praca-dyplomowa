package FragmentsClass.MainModulesFragments.SavedLocations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
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

import java.util.Objects;

import DataBase.DataBaseHelper;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class AddLocationFragment extends Fragment {

    private Context context;
    private TextInputEditText howLocation;
    private ImageView image, buttonComeBack;
    private TextView howCoordinate, titleOfWorld;
    private Button buttonSave;

    private LocationManager locationManager;
    private LocationListener listener;
    private AnimationDrawable roundingWorldAnimation;
    private boolean readCoordinate = false;
    private double latitude, longitude;

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
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_add_location));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howLocation = view.findViewById(R.id.location);
        image = view.findViewById(R.id.image);
        howCoordinate = view.findViewById(R.id.how_coordinate);
        titleOfWorld = view.findViewById(R.id.title_of_world);
        buttonSave = view.findViewById(R.id.button_save);
        buttonComeBack = view.findViewById(R.id.button_come_back);
    }

    @SuppressLint("SetTextI18n")
    private void createListeners() {
        buttonSave.setOnClickListener(v -> validateData());

        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedLocationsFragment()).commit());

        image.setOnClickListener(v -> {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            listener = location -> {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                howCoordinate.setText(ToolClass.generateStringCoordinate(latitude) + "N\n" +
                                      ToolClass.generateStringCoordinate(longitude) + "E");
                image.setImageResource(R.drawable.image_empty_background);
                image.setBackgroundResource(R.drawable.animation_rounding_world);
                roundingWorldAnimation = (AnimationDrawable) image.getBackground();
                roundingWorldAnimation.start();
                titleOfWorld.setText("Prawidłowo odczytano\nwspółrzędne geograficzne!");
                if(!readCoordinate)
                    image.animate().scaleYBy(0.2f).scaleXBy(0.2f).setDuration(3000);
                readCoordinate = true;
            };
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            image.setClickable(false);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        });
    }

    private void validateData() {
        ShowToast toast = new ShowToast();
        if(Objects.requireNonNull(howLocation.getText()).toString().compareTo("")==0)
            toast.showErrorToast(context,"BŁĄD DANYCH!\n  Uzupełnij nazwę lokalizacji!", R.drawable.icon_information);
        else{
            if(!readCoordinate)
                toast.showErrorToast(context,"BŁĄD DANYCH!\n  Odczytaj współrzędne lokalizacji!", R.drawable.icon_information);
            else
                addToDatabase();
        }

    }

    private void addToDatabase() {
        DataBaseHelper db = new DataBaseHelper(context);
        db.addLocation(Objects.requireNonNull(howLocation.getText()).toString(),latitude,longitude);
        ShowToast toast = new ShowToast();
        toast.showSuccessfulToast(context, "SUKCES\n" + "  Pomyślnie zapisałeś lokalizacje!");
        locationManager.removeUpdates(listener);
        roundingWorldAnimation.stop();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedLocationsFragment()).commit();
    }
}


