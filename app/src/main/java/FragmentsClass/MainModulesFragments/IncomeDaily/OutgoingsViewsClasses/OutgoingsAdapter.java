package FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.List;


public class OutgoingsAdapter extends RecyclerView.Adapter<OutgoingsAdapter.OutgoingsViewHolder> {
    private List<OutgoingsItem> outgoingsItem;
    private OutgoingsAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener { ;
        void onUpdateClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OutgoingsAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class OutgoingsViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView howDate, howCategory, howPrice, howDescribe;
        public ImageView editItem, removeItem;

        public OutgoingsViewHolder(View itemView, final OutgoingsAdapter.OnItemClickListener listener) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            howDate=itemView.findViewById(R.id.how_date);
            howCategory=itemView.findViewById(R.id.how_category);
            howPrice=itemView.findViewById(R.id.how_price);
            howDescribe=itemView.findViewById(R.id.how_describe);
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

    public OutgoingsAdapter(List<OutgoingsItem> outgoingsItem) {
        this.outgoingsItem = outgoingsItem;
    }

    @Override
    public OutgoingsAdapter.OutgoingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_outgoing, parent, false);
        OutgoingsAdapter.OutgoingsViewHolder ovh = new OutgoingsAdapter.OutgoingsViewHolder(v, adapterListener);
        return ovh;
    }

    @Override
    public void onBindViewHolder(OutgoingsAdapter.OutgoingsViewHolder holder, int position) {
        OutgoingsItem currentItem = outgoingsItem.get(position);

        holder.image.setImageResource(currentItem.getIOutgoingImage());
        holder.howDate.setText(currentItem.getIOutgoingDate());
        holder.howCategory.setText(holder.howCategory.getText()+currentItem.getIOutgoingCategory());
        holder.howPrice.setText(holder.howPrice.getText()+String.valueOf(currentItem.getIOutgoingPrice()+" zł"));
        holder.howDescribe.setText(currentItem.getIOutgoingDescribe());

    }

    @Override
    public int getItemCount() {
        return outgoingsItem.size();
    }

}
