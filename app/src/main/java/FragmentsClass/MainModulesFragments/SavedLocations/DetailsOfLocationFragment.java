package FragmentsClass.MainModulesFragments.SavedLocations;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;
import HelperClasses.ToolClass;

public class DetailsOfLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap locationMap;
    private Context context;

    private TextView howLocation, howCoordinate;
    private ImageView buttonComeBack;

    private double latitude, longitude;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_details_of_location, container, false);
        assert container != null;
        context = container.getContext();
        findViews(view);
        startSettings();
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
        int id = item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_details_of_locations));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howLocation = view.findViewById(R.id.how_location);
        howCoordinate = view.findViewById(R.id.how_coordinate);
        buttonComeBack = view.findViewById(R.id.button_come_back);
    }

    private void startSettings() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.ToolSharedPreferences.NAME, Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(SharedPreferencesNames.ToolSharedPreferences.POSITION_OF_LOCATION_RV, 0);
        DataBaseHelper db = new DataBaseHelper(context);
        Cursor c = db.getSpecifyLocation(id);
        c.moveToFirst();
        name = c.getString(c.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_NAME_OF_LOCATION));
        latitude = c.getDouble(c.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_LATITUDE));
        longitude = c.getDouble(c.getColumnIndexOrThrow(DataBaseNames.LocationItem.COLUMN_LONGITUDE));

        howLocation.setText(name);
        howCoordinate.setText(ToolClass.generateStringCoordinate(latitude) + "N  " +
                ToolClass.generateStringCoordinate(longitude) + "E");

    }

    private void createListeners() {
        buttonComeBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DailyOfLocationsFragment()).commit());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationMap = googleMap;
        LatLng location = new LatLng(latitude, longitude);
        locationMap.addMarker(new MarkerOptions().position(location).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        locationMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationMap.setMyLocationEnabled(true);
        locationMap.getUiSettings().setMyLocationButtonEnabled(true);
        locationMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
    }
}


