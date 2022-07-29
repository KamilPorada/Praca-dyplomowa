package FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;


public class DropOfWaterAdapter extends RecyclerView.Adapter<DropOfWaterAdapter.DropOfWaterViewHolder> {
    private final List<DropOfWaterItem> dropOfWaterItems;
    public Context context;

    public static class DropOfWaterViewHolder extends RecyclerView.ViewHolder {
        public TextView howRound;
        public ImageView image;
        @SuppressLint("SetTextI18n")
        public DropOfWaterViewHolder(View itemView) {
            super(itemView);
            howRound=itemView.findViewById(R.id.how_round);
            image=itemView.findViewById(R.id.image);
        }
    }
    public DropOfWaterAdapter(List<DropOfWaterItem> DropOfWaterItems) {
        this.dropOfWaterItems=DropOfWaterItems;
    }


    @NonNull
    @Override
    public DropOfWaterAdapter.DropOfWaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_drop_of_water, parent, false);
        context=parent.getContext();
        return new DropOfWaterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DropOfWaterAdapter.DropOfWaterViewHolder holder, int position) {
        DropOfWaterItem currentItem = dropOfWaterItems.get(position);
        holder.howRound.setText("TURA " + currentItem.getIRound());
        holder.image.setImageResource(currentItem.getIImage());
    }

    @Override
    public int getItemCount() {
        return dropOfWaterItems.size();
    }

}