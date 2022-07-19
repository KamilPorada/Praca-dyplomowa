package HelperClasses;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.example.pracadyplomowa.R;

public class ShowToast {

    public void showInformationToast(@LayoutRes int resource, ViewGroup root, Activity activity, Context context, String attention)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(resource, root);

        TextView toastText=view.findViewById(R.id.toast_text);
        toastText.setText(attention);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0, 200);
        toast.setView(view);
        toast.show();
    }

}
