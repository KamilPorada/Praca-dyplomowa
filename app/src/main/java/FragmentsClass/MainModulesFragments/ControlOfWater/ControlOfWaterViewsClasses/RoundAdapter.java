package FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;


public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.RoundViewHolder> {
    private final List<RoundItem> roundItems;
    public Context context;
    
    

    public static class RoundViewHolder extends RecyclerView.ViewHolder {
        public TextView howRound, howHighgroves, howTime;
        @SuppressLint("SetTextI18n")
        public RoundViewHolder(View itemView) {
            super(itemView);
            howRound=itemView.findViewById(R.id.how_round);
            howHighgroves=itemView.findViewById(R.id.how_highgroves);
            howTime=itemView.findViewById(R.id.how_time);
        }
    }
    public RoundAdapter(List<RoundItem> roundItems) {
        this.roundItems=roundItems;
    }


    @NonNull
    @Override
    public RoundAdapter.RoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_rounds, parent, false);
        context=parent.getContext();
        return new RoundViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundAdapter.RoundViewHolder holder, int position) {
        RoundItem currentItem = roundItems.get(position);
        holder.howRound.setText("TURA " + currentItem.getIId());
        holder.howHighgroves.setText(currentItem.getIHighgroves());
        holder.howTime.setText(currentItem.getITime()+" min");
    }

    @Override
    public int getItemCount() {
        return roundItems.size();
    }

}