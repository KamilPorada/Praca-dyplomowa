package FragmentsClass.BottomFragments;

import android.app.Dialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pracadyplomowa.R;

import HelperClasses.InformationDialog;

public class AboutApplicationFragment extends Fragment {

    private Context context;
    private ImageView lawButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert container != null;
        context=container.getContext();
        View view=inflater.inflate(R.layout.layout_about_application_fragment,container,false);
        programLawButton(view);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void programLawButton(View view) {
        lawButton=view.findViewById(R.id.law_button);

        lawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLowDialog();
            }
        });
    }

    private void openLowDialog() {
        Dialog lowDialog = new Dialog(context);
        lowDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        lowDialog.setContentView(R.layout.dialog_paragraph);
        lowDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lowDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        lowDialog.show();
        createDialogListeners(lowDialog);
    }

    private void createDialogListeners(Dialog lowDialog) {
        Button okButton = lowDialog.findViewById(R.id.ok_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowDialog.dismiss();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.information) {
            InformationDialog informationDialog = new InformationDialog();
            informationDialog.openInformationDialog(context, getResources().getString(R.string.describes_about_application));
        }
        return super.onOptionsItemSelected(item);
    }

}
