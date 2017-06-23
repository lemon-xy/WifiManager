package pear.lemon.wifi.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 字符文本类
 */
public class IconTextView extends AppCompatTextView {

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "icomoon.ttf");
        setTypeface(face);
    }
}