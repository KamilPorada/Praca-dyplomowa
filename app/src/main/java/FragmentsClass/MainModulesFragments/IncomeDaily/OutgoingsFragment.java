package FragmentsClass.MainModulesFragments.IncomeDaily;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pracadyplomowa.R;

import java.util.ArrayList;
import java.util.Date;

import DataBase.DataBaseHelper;
import DataBase.DataBaseNames;
import OthersClass.InformationDialog;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsAdapter;
import FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses.OutgoingsItem;

public class OutgoingsFragment extends Fragment {
    
    private Context context;
    private ArrayList<OutgoingsItem> OutgoingsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OutgoingsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_outgoings, container, false);
        context=container.getContext();
        findViews(view);
        startSettings();
        loadData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.tollbar_menu_add_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_income_daily));
            }break;
            case R.id.add_item:
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddOutgoingsFragment()).commit();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        adapter = new OutgoingsAdapter(OutgoingsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OutgoingsAdapter.OnItemClickListener() {
            @Override
            public void onUpdateClick(int position) {
                sendData(position);
            }

            @Override
            public void onDeleteClick(int position) {
                deleteData(position);
            }
        });
    }

    private void sendData(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("category",OutgoingsList.get(position).getIOutgoingCategory());
        bundle.putDouble("price",OutgoingsList.get(position).getIOutgoingPrice());
        bundle.putString("date",OutgoingsList.get(position).getIOutgoingDate());
        bundle.putString("describe",OutgoingsList.get(position).getIOutgoingDescribe());
        bundle.putInt("position",position);
        UpdateOutgoingsFragment fragment = new UpdateOutgoingsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    private void deleteData(int position) {
        DataBaseHelper db = new DataBaseHelper(context);
        Cursor cursor = db.getItemID(DataBaseNames.OutgoingsItem.TABLE_NAME,DataBaseNames.OutgoingsItem._ID,
                DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD,OutgoingsList.get(position).getIOutgoingPasswordKey());
        int id=0;
        while (cursor.moveToNext())
        {
            id=cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem._ID));
        }
        db.deleteItem(DataBaseNames.OutgoingsItem.TABLE_NAME,id);
        Fragment fragment = new OutgoingsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        loadData();
    }

    private void loadData() {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        Cursor k =dbHelper.getOutgoingItems();
        Date calendar = new Date();
        int currentYear=calendar.getYear()+1900;
        int image=0;
        String date, category, describe, passwordKey;
        double price;
        while(k.moveToNext()) {
            date=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATE_OF_OUTGOING));
            category=k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_CATEGORY_OF_OUTGOING));
            price = k.getDouble(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_PRICE_OF_OUTGOING));
            describe = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DESCRIBE_OF_OUTGOING));
            passwordKey = k.getString(k.getColumnIndexOrThrow(DataBaseNames.OutgoingsItem.COLUMN_DATA_PASWORD));
            switch (category)
            {
                case "Konstrukcje tuneli":
                {
                    image=R.drawable.image_highgrove;
                }break;
                case "Folie ogrodnicze":
                {
                    image=R.drawable.image_foil;
                }break;
                case "Hydraulika w tunelach":
                {
                    image=R.drawable.image_water;
                }break;
                case "Paliki do tuneli":
                {
                    image=R.drawable.image_pegs;
                }break;
                case "Nasiona papryki":
                {
                    image=R.drawable.image_seeds;
                }break;
                case "Sadzonki papryki":
                {
                    image=R.drawable.image_plant;
                }break;
                case "Pestycydy":
                {
                    image=R.drawable.image_pesticides;
                }break;
                case "Nawozy":
                {
                    image=R.drawable.image_fertilizer;
                }break;
                case "Maszyny rolnicze":
                {
                    image=R.drawable.image_machine;
                }break;
                case "Narzędzia ogrodnicze":
                {
                    image=R.drawable.image_tools;
                }break;
                case "Inne":
                {
                    image=R.drawable.icon_question;
                }break;
            }
            int year=getYear(date);
            if(currentYear==year)
                OutgoingsList.add(new OutgoingsItem(image, category, describe, price, date, passwordKey));
        }
    }

    private int getYear(String date) {
        char[] charDate = date.toCharArray();
        String stringYear = Character.toString(charDate[6]) + Character.toString(charDate[7]) +
                Character.toString(charDate[8]) + Character.toString(charDate[9]);
        int year = Integer.parseInt(stringYear);
        return year;
    }
}


