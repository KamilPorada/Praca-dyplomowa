package FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses;

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



public class WateringAdapter extends RecyclerView.Adapter<WateringAdapter.WateringViewHolder> {
    private final List<WateringItem> wateringItem;
    private WateringAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onDoneClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(WateringAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class WateringViewHolder extends RecyclerView.ViewHolder {
        public TextView howHighgroves, howTime, howEfficiencyOfPump, howUsagesOfWater;
        public ImageView doItem, removeItem;

        public WateringViewHolder(View itemView, final WateringAdapter.OnItemClickListener listener) {
            super(itemView);
            howHighgroves=itemView.findViewById(R.id.how_highgroves);
            howTime=itemView.findViewById(R.id.how_time);
            howEfficiencyOfPump=itemView.findViewById(R.id.how_efficiency_of_pump);
            howUsagesOfWater=itemView.findViewById(R.id.how_usage_of_water);
            doItem=itemView.findViewById(R.id.do_item);
            removeItem=itemView.findViewById(R.id.remove_item);

            doItem.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDoneClick(position);
                    }
                }
            });
            removeItem.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public WateringAdapter(List<WateringItem> WateringItem) {
        this.wateringItem = WateringItem;
    }

    @NonNull
    @Override
    public WateringAdapter.WateringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_watering, parent, false);
        return new WateringViewHolder(v, adapterListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(WateringAdapter.WateringViewHolder holder, int position) {
        WateringItem currentItem = wateringItem.get(position);

        holder.howHighgroves.setText(currentItem.getIHighgroves());
        holder.howTime.setText(currentItem.getITime());
        holder.howEfficiencyOfPump.setText(String.valueOf(currentItem.getIEfficiency()) + " l/min");
        holder.howUsagesOfWater.setText(currentItem.getIUsageOfWater());

    }

    @Override
    public int getItemCount() {
        return wateringItem.size();
    }

}
