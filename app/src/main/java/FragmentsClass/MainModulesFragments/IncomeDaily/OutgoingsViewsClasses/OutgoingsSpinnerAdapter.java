package FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;


public class OutgoingsSpinnerAdapter extends ArrayAdapter<OutgoingsSpinnerItem> {

    public OutgoingsSpinnerAdapter(Context context, ArrayList<OutgoingsSpinnerItem> List) {
        super(context, 0, List);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_outgoings_row, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text_view_row);

        OutgoingsSpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            text.setText(currentItem.getText());
        }

        return convertView;
    }
}
