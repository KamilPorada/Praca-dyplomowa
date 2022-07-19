package FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;


public class CatalogOfPesticideAdapter extends RecyclerView.Adapter<CatalogOfPesticideAdapter.CatalogOfPesticideViewHolder> {
    private final List<CatalogOfPesticideItem> catalogOfPesticideItems;
    private OnItemClickListener adapterListener;
    public Context context;
    private static int modeOfCatalogOfPesticide=1;

    public interface OnItemClickListener {
        void onShowInformation(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class CatalogOfPesticideViewHolder extends RecyclerView.ViewHolder {
        public TextView howPesticides;
        public Button informationButton;
        @SuppressLint("SetTextI18n")
        public CatalogOfPesticideViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            howPesticides=itemView.findViewById(R.id.how_pesticides);
            informationButton=itemView.findViewById(R.id.button_information);
            if(modeOfCatalogOfPesticide==0)
                informationButton.setText("WYBIERZ");
            else
                informationButton.setText("SZCZEGÓŁY");
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
        context=parent.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("TOOL_SHARED_PREFERENCES",Context.MODE_PRIVATE);
        modeOfCatalogOfPesticide = sharedPreferences.getInt("CATALOG_OF_PESTICIDE_OPEN_MODE", 0);
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