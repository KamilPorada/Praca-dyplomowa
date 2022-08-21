package FragmentsClass.MainModulesFragments.SavedLocations;

import android.Manifest;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;
import HelperClasses.ShowToast;
import HelperClasses.ToolClass;

public class MarketsOnMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap locationMap;
    private Context context;

    private ImageView buttonComeBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_markets_on_map, container, false);
        assert container != null;
        context = container.getContext();
        findViews(view);
        startSettings();
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
        buttonComeBack = view.findViewById(R.id.button_come_back);
    }

    private void startSettings() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void createListeners() {
        buttonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedLocationsFragment()).commit();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationMap = googleMap;
        DataBaseHelper db = new DataBaseHelper(context);
        Cursor c = db.getMarkets();
        while (c.moveToNext())
        {
            String name = c.getString(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_NAME));
            double latitude = c.getDouble(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_LATITUDE));
            double longitude = c.getDouble(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_LONGITUDE));

            LatLng location = new LatLng(latitude, longitude);
            Marker marker = locationMap.addMarker(new MarkerOptions().position(location).title(name));
            marker.setSnippet(c.getString(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_ADDRESS)));


            locationMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String title = marker.getTitle();
                    DataBaseHelper db = new DataBaseHelper(context);
                    Cursor c = db.getSpecifyMarkets(title);
                    c.moveToFirst();
                    ShowToast toast = new ShowToast();
                    toast.showSuccessfulToast(context,c.getString(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_VOIVODESHIP)));
                    return false;
                }
            });

            locationMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
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
        locationMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.06, 19.2), 5.8f));
    }
}


