package FragmentsClass.BottomFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import HelperClasses.InformationDialog;
import HelperClasses.ShowAttention;
import HelperClasses.StatisticsHelper;
import HelperClasses.ToolClass;

public class FarmFragment extends Fragment {

    private Context context;

    private TextView howName, howHighgroves, howField, howMoney;
    private EditText editName, editHighgroves, editField;
    private Button cancelButton, acceptButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context = container.getContext();
        View view=inflater.inflate(R.layout.layout_farm_fragment,container,false);
        findViews(view);
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
        inflater.inflate(R.menu.tollbar_menu_farm, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.information:
            {
                InformationDialog informationDialog = new InformationDialog();
                informationDialog.openInformationDialog(context,getResources().getString(R.string.describes_farm));
            }break;
            case R.id.change_farm_data:
            {
                openChangeFarmDataDialog();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews(View view) {
        howName=view.findViewById(R.id.how_name);
        howHighgroves=view.findViewById(R.id.how_highgroves);
        howField=view.findViewById(R.id.how_field);
        howMoney=view.findViewById(R.id.how_money);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FARM_DATA",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("NAME", "");
        String highgroves = sharedPreferences.getString("HIGHGROVES",howHighgroves.getText().toString());
        String field = sharedPreferences.getString("FIELD", howField.getText()+" ha");

        howMoney.setText(Math.round(StatisticsHelper.calculateIncomeFromHighgrove(context, ToolClass.getActualYear(), 1) * 100.0) / 100.0 + " zł");
        howName.setText("Właściciel gospodarstwa\n"+name);
        howHighgroves.setText(highgroves);
        howField.setText(field+" ha");
    }

    private void openChangeFarmDataDialog() {
        Dialog changeDataDialog = new Dialog(context);
        changeDataDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        changeDataDialog.setContentView(R.layout.dialog_change_farm_data);
        changeDataDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeDataDialog.show();
        findChangeSeasonDialogViews(changeDataDialog);
        createAndAddListeners(changeDataDialog);
    }

    private void findChangeSeasonDialogViews(Dialog changeDataDialog) {
        editName=changeDataDialog.findViewById(R.id.edit_name);
        editHighgroves=changeDataDialog.findViewById(R.id.edit_highgroves);
        editField=changeDataDialog.findViewById(R.id.edit_field);
        cancelButton=changeDataDialog.findViewById(R.id.cancel_button);
        acceptButton=changeDataDialog.findViewById(R.id.accept_button);
    }

    private void createAndAddListeners(Dialog changeDataDialog) {
        @SuppressLint("NonConstantResourceId") View.OnClickListener listener = v -> {
            int id=v.getId();
            switch (id)
            {
                case R.id.accept_button:
                {
                    validateData();
                    changeDataDialog.dismiss();
                }break;
                case R.id.cancel_button:
                {
                    changeDataDialog.cancel();
                }
            }
        };
        acceptButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }

    private void validateData() {
        ShowAttention showAttention = new ShowAttention();
        if (String.valueOf(editName.getText()).compareTo("")==0 ||
            String.valueOf(editHighgroves.getText()).compareTo("")==0 ||
            String.valueOf(editField.getText()).compareTo("")==0)
                showAttention.showToast(R.layout.toast_layout,null, requireActivity(),context,"BŁĄD EDYCJI!\nUzupełnij wszystkie pola!");
        else
            editDataFarm();
    }

    @SuppressLint("SetTextI18n")
    private void editDataFarm() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FARM_DATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("NAME", editName.getText().toString());
        editor.putString("HIGHGROVES", editHighgroves.getText().toString());
        editor.putString("FIELD", editField.getText().toString());
        editor.apply();

        howName.setText("Właściciel gospodarstwa\n"+editName.getText());
        howHighgroves.setText(editHighgroves.getText());
        howField.setText(editField.getText()+" ha");
    }


}
