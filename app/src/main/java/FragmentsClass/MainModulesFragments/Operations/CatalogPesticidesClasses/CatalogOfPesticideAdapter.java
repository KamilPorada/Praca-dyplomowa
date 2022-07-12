package FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses;


import android.annotation.SuppressLint;
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

import FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses.CatalogOfPesticideItem;


public class CatalogOfPesticideAdapter extends RecyclerView.Adapter<CatalogOfPesticideAdapter.CatalogOfPesticideViewHolder> {
    private final List<CatalogOfPesticideItem> catalogOfPesticideItems;
    private OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onShowInformation(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class CatalogOfPesticideViewHolder extends RecyclerView.ViewHolder {
        public TextView howPesticides;
        public Button informationButton, selectedButton;

        public CatalogOfPesticideViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            howPesticides=itemView.findViewById(R.id.how_pesticides);
            informationButton=itemView.findViewById(R.id.button_information);

            informationButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onShowInformation(position);
                    }
                }
            });
        }
    }
    public CatalogOfPesticideAdapter(List<CatalogOfPesticideItem> catalogOfPesticideItems) {
        this.catalogOfPesticideItems=catalogOfPesticideItems;
    }


    @NonNull
    @Override
    public CatalogOfPesticideAdapter.CatalogOfPesticideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_catalog_of_pesticides, parent, false);
        return new CatalogOfPesticideViewHolder(v, adapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogOfPesticideAdapter.CatalogOfPesticideViewHolder holder, int position) {
        CatalogOfPesticideItem currentItem = catalogOfPesticideItems.get(position);
        holder.howPesticides.setText(currentItem.getNameOfPesticide());
    }

    @Override
    public int getItemCount() {
        return catalogOfPesticideItems.size();
    }

}