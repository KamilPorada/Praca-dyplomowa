package FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;


public class TradePepperAdapter extends RecyclerView.Adapter<TradePepperAdapter.TradePepperViewHolder> {
    private List<TradePepperItem> tradePepperItems;
    private OnItemClickListener adapterListener;

    public interface OnItemClickListener { ;
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

            editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUpdateClick(position);
                        }
                    }
                }
            });
            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public TradePepperAdapter(List<TradePepperItem> tradePepperItems) {
        this.tradePepperItems = tradePepperItems;
    }

    @Override
    public TradePepperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_trade_of_pepper, parent, false);
        TradePepperViewHolder tdvh = new TradePepperViewHolder(v, adapterListener);
        return tdvh;
    }

    @Override
    public void onBindViewHolder(TradePepperViewHolder holder, int position) {
        TradePepperItem currentItem = tradePepperItems.get(position);
        holder.image.setImageResource(currentItem.getIPepperImage());
        holder.date.setText(currentItem.getIDate());
        holder.clas.setText(holder.clas.getText()+currentItem.getIPepperClass());
        holder.price.setText(holder.price.getText()+String.valueOf(currentItem.getIPrice())+"zł");
        holder.weight.setText(holder.weight.getText()+String.valueOf(currentItem.getIweight())+"kg");
        holder.total_sum.setText(holder.total_sum.getText()+currentItem.getITotalSum()+"zł");
        holder.place.setText(currentItem.getIPlace());
    }

    @Override
    public int getItemCount() {
        return tradePepperItems.size();
    }

}