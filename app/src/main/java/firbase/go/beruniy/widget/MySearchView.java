package firbase.go.beruniy.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import firbase.go.beruniy.R;
import firbase.go.beruniy.mold.MoldApi;

public class MySearchView extends SearchView {


    @SuppressWarnings("deprecation")
    public MySearchView(Context context) {
        super(context);
        try {
            AppCompatAutoCompleteTextView v = (AppCompatAutoCompleteTextView)
                    findViewById(R.id.search_src_text);
            v.setTextColor(MoldApi.getColor(R.color.toolbar_icon_color_silver_dark));

            changeIconColor(android.support.v7.appcompat.R.id.search_button, R.color.toolbar_icon_color_silver_dark);
            changeIconColor(android.support.v7.appcompat.R.id.search_go_btn, R.color.toolbar_icon_color_silver_dark);
            changeIconColor(android.support.v7.appcompat.R.id.search_close_btn, R.color.toolbar_icon_color_silver_dark);
            changeIconColor(android.support.v7.appcompat.R.id.search_voice_btn, R.color.toolbar_icon_color_silver_dark);
            changeIconColor(android.support.v7.appcompat.R.id.search_mag_icon, R.color.toolbar_icon_color_silver_dark);
        } catch (Exception e) {
        }
    }

    private void changeIconColor(int resId, @ColorRes int colorResId) {
        ImageView icon = (ImageView) findViewById(resId);
        Drawable drawable = icon.getDrawable().getConstantState().newDrawable().mutate();
        drawable.setColorFilter(MoldApi.getColor(colorResId), PorterDuff.Mode.SRC_IN);
        icon.setImageDrawable(drawable);
    }
}
