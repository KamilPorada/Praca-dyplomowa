package FragmentsClass.MainModulesFragments.Notes.NotesViewClasses;

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


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final List<NoteItem> NoteItem;
    private NoteAdapter.OnItemClickListener adapterListener;

    public interface OnItemClickListener {
        void onUpdateClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(NoteAdapter.OnItemClickListener listener) {
        adapterListener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView howTitle, howDate, howDescribe;
        public ImageView editItem, removeItem;

        public NoteViewHolder(View itemView, final NoteAdapter.OnItemClickListener listener) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            howTitle=itemView.findViewById(R.id.how_title);
            howDate=itemView.findViewById(R.id.how_date);
            howDescribe=itemView.findViewById(R.id.how_describe);
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

    public NoteAdapter(List<NoteItem> NoteItem) {
        this.NoteItem = NoteItem;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_note, parent, false);
        return new NoteViewHolder(v, adapterListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        NoteItem currentItem = NoteItem.get(position);

        holder.image.setImageResource(currentItem.getIImage());
        holder.howTitle.setText(currentItem.getITitle());
        holder.howDate.setText(currentItem.getIDate());
        holder.howDescribe.setText(currentItem.getIDescribe());

    }

    @Override
    public int getItemCount() {
        return NoteItem.size();
    }

}
