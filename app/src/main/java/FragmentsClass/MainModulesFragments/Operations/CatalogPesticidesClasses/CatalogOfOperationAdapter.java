package FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;


public class CatalogOfOperationAdapter extends RecyclerView.Adapter<CatalogOfOperationAdapter.CatalogOfOperationViewHolder> {
    private final List<CatalogOfOperationItem> catalogOfOperationItems;
    private OnItemClickListener adapterListener;
    public Context context;

    public interface OnItemClickListener {
        void onShowInformation(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class CatalogOfOperationViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, howDate, howHour, howGrace, howStatus, howPesticide;
        public Button buttonDetailsOfOperation;
        public CatalogOfOperationViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            howDate=itemView.findViewById(R.id.how_date);
            howHour=itemView.findViewById(R.id.how_hour);
            howGrace=itemView.findViewById(R.id.how_grace);
            howStatus=itemView.findViewById(R.id.how_status);
            howPesticide=itemView.findViewById(R.id.how_pesticide);
            buttonDetailsOfOperation=itemView.findViewById(R.id.button_details_of_operation);

            buttonDetailsOfOperation.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onShowInformation(position);
                    }
                }
            });
        }
    }
    public CatalogOfOperationAdapter(List<CatalogOfOperationItem> catalogOfOperationItems) {
        this.catalogOfOperationItems=catalogOfOperationItems;
    }


    @NonNull
    @Override
    public CatalogOfOperationAdapter.CatalogOfOperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_catalog_of_operations, parent, false);
        return new CatalogOfOperationViewHolder(v, adapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogOfOperationAdapter.CatalogOfOperationViewHolder holder, int position) {
        CatalogOfOperationItem currentItem = catalogOfOperationItems.get(position);
        holder.image.setImageResource(currentItem.getImage());
        holder.title.setText(currentItem.getDescribe());
        holder.howDate.setText(currentItem.getDate());
        holder.howHour.setText(currentItem.getTime());
        holder.howStatus.setText(currentItem.getStatus());
        holder.howGrace.setText(currentItem.getGrace());
        holder.howPesticide.setText(currentItem.getPesticide());
    }

    @Override
    public int getItemCount() {
        return catalogOfOperationItems.size();
    }

}