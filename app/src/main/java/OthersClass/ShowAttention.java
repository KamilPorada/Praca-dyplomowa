package OthersClass;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.example.pracadyplomowa.R;

public class ShowAttention {

    public void showToast(@LayoutRes int resource, ViewGroup root, Activity activity, Context context, String attention)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(resource, root);

        TextView toastText=view.findViewById(R.id.toast_text);
        toastText.setText(attention);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

}
