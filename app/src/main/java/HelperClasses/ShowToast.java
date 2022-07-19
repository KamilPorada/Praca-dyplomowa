package HelperClasses;

import android.content.Context;
import android.view.Gravity;

import com.example.pracadyplomowa.R;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class ShowToast {

    public void showErrorToast(Context context, String attention, int icon)
    {

        SuperActivityToast.create(context, new Style(), Style.TYPE_STANDARD)
                .setIconResource(Style.ICONPOSITION_LEFT, icon)
                .setGravity(Gravity.BOTTOM,0, 0)
                .setText("  "+attention)
                .setTextSize(Style.TEXTSIZE_MEDIUM)
                .setDuration(Style.DURATION_SHORT)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_FLY).show();
    }

    public void showSuccessfulToast(Context context, String attention)
    {

        SuperActivityToast.create(context, new Style(), Style.TYPE_STANDARD)
                .setIconResource(Style.ICONPOSITION_LEFT, R.drawable.icon_done)
                .setGravity(Gravity.BOTTOM,0, 0)
                .setText("  "+attention)
                .setDuration(Style.DURATION_SHORT)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN))
                .setAnimations(Style.ANIMATIONS_FLY).show();
    }

}
