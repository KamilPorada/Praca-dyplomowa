package FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses;


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


public class TradePepperAdapter extends RecyclerView.Adapter<TradePepperAdapter.TradePepperViewHolder> {
    private final List<TradePepperItem> tradePepperItems;
    private OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onUpdateClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class TradePepperViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView date, clas, price, weight, total_sum, place;
        public ImageView editItem, removeItem;

        public TradePepperViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            date=itemView.findViewById(R.id.how_date);
            clas=itemView.findViewById(R.id.how_class);
            price=itemView.findViewById(R.id.how_price);
            weight=itemView.findViewById(R.id.how_weight);
            total_sum=itemView.findViewById(R.id.how_total_sum);
            place=itemView.findViewById(R.id.how_places);
            editItem=itemView.findViewById(R.id.edit_item);
            removeItem=itemView.findViewById(R.id.remove_item);

            editItem.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onUpdateClick(position);
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

    public TradePepperAdapter(List<TradePepperItem> tradePepperItems) {
        this.tradePepperItems = tradePepperItems;
    }

    @NonNull
    @Override
    public TradePepperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_trade_of_pepper, parent, false);
        return new TradePepperViewHolder(v, adapterListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TradePepperViewHolder holder, int position) {
        TradePepperItem currentItem = tradePepperItems.get(position);
        holder.image.setImageResource(ToolClass.getPepperDrawable(currentItem.getIColor()));
        holder.date.setText(currentItem.getIDate());
        holder.price.setText(String.valueOf(currentItem.getIPrice())+"zł");
        holder.weight.setText(String.valueOf(currentItem.getIweight())+"kg");
        holder.total_sum.setText(String.valueOf(currentItem.getITotalSum())+"zł");
        holder.place.setText(currentItem.getIPlace());

        switch (currentItem.getIPepperClass())
        {
            case 1:
            {
                holder.clas.setText("1");
            }break;
            case 2:
            {
                holder.clas.setText("2");
            }break;
            case 3:
            {
                holder.clas.setText("krojona");
            }break;
        }
    }

    @Override
    public int getItemCount() {
        return tradePepperItems.size();
    }

}