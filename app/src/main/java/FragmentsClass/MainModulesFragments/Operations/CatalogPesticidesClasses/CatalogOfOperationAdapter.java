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

import java.util.Calendar;
import java.util.List;

import HelperClasses.ToolClass;


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
        public ImageView image, imageSkull;
        public TextView title, howDate, howHour, howGrace, howStatus, howPesticide, attentionOfGrace;
        public Button buttonDetailsOfOperation;
        public CatalogOfOperationViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            imageSkull=itemView.findViewById(R.id.image_skull);
            title=itemView.findViewById(R.id.title);
            howDate=itemView.findViewById(R.id.how_date);
            howHour=itemView.findViewById(R.id.how_hour);
            howGrace=itemView.findViewById(R.id.how_grace);
            howStatus=itemView.findViewById(R.id.how_status);
            howPesticide=itemView.findViewById(R.id.how_pesticide);
            attentionOfGrace=itemView.findViewById(R.id.attention_of_grace);
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
        if(!checkGrace(currentItem.getEndOfGrace(), currentItem.getStatus()))
        {
            holder.attentionOfGrace.setVisibility(View.INVISIBLE);
            holder.imageSkull.setVisibility(View.INVISIBLE);
        }
    }

    private boolean checkGrace(String endOfGrace, String status) {
        Calendar grace = Calendar.getInstance();
        grace.set(Calendar.DAY_OF_MONTH, ToolClass.getDay(endOfGrace));
        grace.set(Calendar.MONTH,ToolClass.getMonth(endOfGrace));
        grace.set(Calendar.YEAR,ToolClass.getYear(endOfGrace));

        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, ToolClass.getActualDay());
        today.set(Calendar.MONTH,ToolClass.getActualMonth());
        today.set(Calendar.YEAR,ToolClass.getActualYear());

        if(grace.after(today) && status.compareTo("Wykonano")==0)
            return true;
        else
            return false;
    }

    @Override
    public int getItemCount() {
        return catalogOfOperationItems.size();
    }

}