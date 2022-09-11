package FragmentsClass.MainModulesFragments.SavedLocations;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
            Drawable vectorDrawable = context.getDrawable(R.drawable.icon_market);
            int h = ((int) Utils.convertDpToPixel(70));
            int w = ((int) Utils.convertDpToPixel(60));
            vectorDrawable.setBounds(0, 0, w, h);
            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bm);
            vectorDrawable.draw(canvas);
            Marker marker = locationMap.addMarker(new MarkerOptions().position(location).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            marker.hideInfoWindow();

            locationMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String title = marker.getTitle();
                    openMarketDialog(title);
                    marker.setTitle("");
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

    private void openMarketDialog(String title) {
        Dialog marketDialog = new Dialog(context);
        marketDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        marketDialog.setContentView(R.layout.dialog_details_of_market);
        marketDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        marketDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        marketDialog.show();

        TextView howLocation = marketDialog.findViewById(R.id.how_location);
        TextView howAddress = marketDialog.findViewById(R.id.how_address);
        TextView howEmail = marketDialog.findViewById(R.id.how_email);
        TextView howTelephone = marketDialog.findViewById(R.id.how_telephone);
        Button btnComeBack = marketDialog.findViewById(R.id.btn_come_back);

        DataBaseHelper db = new DataBaseHelper(context);
        Cursor c = db.getSpecifyMarkets(title);
        c.moveToFirst();

        howLocation.setText(title);
        howAddress.setText(c.getString(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_ADDRESS)));
        howEmail.setText(c.getString(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_EMAIL)));
        howTelephone.setText(c.getString(c.getColumnIndexOrThrow(DataBaseNames.MarketItem.COLUMN_NUMBER)));

        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketDialog.dismiss();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MarketsOnMapFragment()).commit();
            }
        });
    }
}


