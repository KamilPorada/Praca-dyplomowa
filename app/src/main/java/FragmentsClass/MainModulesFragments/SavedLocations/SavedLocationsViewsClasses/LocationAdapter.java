package FragmentsClass.MainModulesFragments.SavedLocations.SavedLocationsViewsClasses;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;

import HelperClasses.ToolClass;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private final List<LocationItem> locationItem;
    private LocationAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onShowClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(LocationAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        public TextView howLocation, howCoordinate;
        public ImageView showLocationButton, removeButton;

        public LocationViewHolder(View itemView, final LocationAdapter.OnItemClickListener listener) {
            super(itemView);
            howLocation = itemView.findViewById(R.id.how_location);
            howCoordinate = itemView.findViewById(R.id.how_coordinate);
            showLocationButton = itemView.findViewById(R.id.show_location_button);
            removeButton = itemView.findViewById(R.id.remove_button);


            showLocationButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onShowClick(position);
                    }
                }
            });

            removeButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public LocationAdapter(List<LocationItem> locationItem) {
        this.locationItem = locationItem;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_location, parent, false);
        return new LocationViewHolder(v, adapterListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(LocationAdapter.LocationViewHolder holder, int position) {
        LocationItem currentItem = locationItem.get(position);
        holder.howLocation.setText(currentItem.getILocationName());
        holder.howCoordinate.setText(ToolClass.generateStringCoordinate(currentItem.getILatitude()) + "N  " +
                                     ToolClass.generateStringCoordinate(currentItem.getILongitude()) + "E");

    }

    @Override
    public int getItemCount() {
        return locationItem.size();
    }

}
