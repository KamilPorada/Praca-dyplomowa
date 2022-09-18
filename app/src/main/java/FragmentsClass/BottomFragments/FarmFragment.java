package FragmentsClass.BottomFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import DataBase.SharedPreferencesNames;
import HelperClasses.InformationDialog;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class FarmFragment extends Fragment {

    private Context context;
    private TextView howOwner, howHighgroves, howField, howMoney, howOutgoing;
    private ImageView editOwnerButton, editHighgrovesButton, editFieldButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context = container.getContext();
        View view=inflater.inflate(R.layout.layout_farm_fragment,container,false);
        findViews(view);
        createListeners();
        loadData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_farm));
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howOwner=view.findViewById(R.id.how_owner);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        howField=view.findViewById(R.id.how_field);
        howMoney=view.findViewById(R.id.how_money);
        howOutgoing=view.findViewById(R.id.how_outgoing);
        editOwnerButton=view.findViewById(R.id.edit_owner_button);
        editHighgrovesButton=view.findViewById(R.id.edit_highgroves_button);
        editFieldButton=view.findViewById(R.id.edit_field_button);
    }

    private void createListeners() {
        View.OnClickListener listener = v -> {
            int id = v.getId();
            openChangeFarmDataDialog(id);
        };
        editOwnerButton.setOnClickListener(listener);
        editHighgrovesButton.setOnClickListener(listener);
        editFieldButton.setOnClickListener(listener);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.FarmData.NAME,Context.MODE_PRIVATE);
        String owner = sharedPreferences.getString(SharedPreferencesNames.FarmData.OWNER, "-");
        String highgroves = sharedPreferences.getString(SharedPreferencesNames.FarmData.HIGHGROVES,"0");
        String field = sharedPreferences.getString(SharedPreferencesNames.FarmData.FIELD, "0.0") + " ha";
        @SuppressLint("DefaultLocale") String money = String.format("%.2f", Math.round(StatisticsHelper.getMoneyFromTrade(context,ToolClass.getActualYear()) * 100.0) / 100.0) + " zł";
        @SuppressLint("DefaultLocale") String outgoing = String.format("%.2f", Math.round(StatisticsHelper.getMoneyFromOutgoings(context,ToolClass.getActualYear()) * 100.0) / 100.0) + " zł";
        howOwner.setText(owner);
        howHighgroves.setText(highgroves);
        howField.setText(field);
        howMoney.setText(money);
        howOutgoing.setText(outgoing);
    }

    private void openChangeFarmDataDialog(int id) {
        Dialog changeDataDialog = new Dialog(context);
        changeDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        changeDataDialog.setContentView(R.layout.dialog_change_farm_data);
        changeDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeDataDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        changeDataDialog.show();
        createAndAddListeners(changeDataDialog, id);
    }

    @SuppressLint("NonConstantResourceId")
    private void createAndAddListeners(Dialog changeDataDialog, int id) {
        ImageView image = changeDataDialog.findViewById(R.id.image);
        TextView title = changeDataDialog.findViewById(R.id.title);
        TextInputEditText howChangeValue = changeDataDialog.findViewById(R.id.how_change_value);
        Button acceptButton = changeDataDialog.findViewById(R.id.accept_button);
        Button cancelButton = changeDataDialog.findViewById(R.id.cancel_button);

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesNames.FarmData.NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        switch (id) {
            case R.id.edit_owner_button: {
                image.setImageResource(R.drawable.image_owner);
                title.setText("Edycja właściciela\ngospodarstwa");
                howChangeValue.setHint("[Imię i nazwisko]");
                howChangeValue.setInputType(0x00000001);
            }break;
            case R.id.edit_highgroves_button: {
                image.setImageResource(R.drawable.image_highgrove);
                title.setText("Edycja ilości\ntuneli");
                howChangeValue.setHint("[Ilość sztuk]");
                howChangeValue.setInputType(0x00000002);
            }break;
            case R.id.edit_field_button: {
                image.setImageResource(R.drawable.image_area_answer);
                title.setText("Edycja powierzchni\ngospodarstwa");
                howChangeValue.setHint("[ha]");
                howChangeValue.setInputType(0x00002002);
            }break;
        }

        acceptButton.setOnClickListener(v -> {
            if (!(Objects.requireNonNull(howChangeValue.getText()).toString().compareTo("") == 0)) {
                switch (id) {
                    case R.id.edit_owner_button: {
                        editor.putString(SharedPreferencesNames.FarmData.OWNER, howChangeValue.getText().toString());
                    }
                    break;
                    case R.id.edit_highgroves_button: {
                        editor.putString(SharedPreferencesNames.FarmData.HIGHGROVES, howChangeValue.getText().toString());
                    }
                    break;
                    case R.id.edit_field_button: {
                        editor.putString(SharedPreferencesNames.FarmData.FIELD, howChangeValue.getText().toString());
                    }
                    break;
                }
                editor.apply();
                changeDataDialog.dismiss();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FarmFragment()).commit();
            }
        });

        cancelButton.setOnClickListener(v -> changeDataDialog.dismiss());
    }
}
