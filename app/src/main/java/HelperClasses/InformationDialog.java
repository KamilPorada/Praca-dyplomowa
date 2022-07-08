package HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.pracadyplomowa.R;

public class InformationDialog {
    public void openInformationDialog(Context context, String describes) {
        Dialog InformationDialog = new Dialog(context);
        InformationDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        InformationDialog.setContentView(R.layout.dialog_information);
        InformationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        InformationDialog.show();
        findInformationDialogViews(InformationDialog, describes);
    }

    private void findInformationDialogViews(Dialog informationDialog, String describes) {
        TextView information=informationDialog.findViewById(R.id.information);
        Button comeBackButton=informationDialog.findViewById(R.id.come_back_button);
        createAndAddListeners(informationDialog, information, comeBackButton, describes);
    }

    private void createAndAddListeners(Dialog informationDialog, TextView information, Button comeBackButton, String describes) {
        information.setText(describes);
        comeBackButton.setOnClickListener(v -> informationDialog.dismiss());
    }
}
