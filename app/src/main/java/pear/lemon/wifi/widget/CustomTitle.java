package pear.lemon.wifi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import pear.lemon.wifi.R;

/**
 * 自定义标题
 */

public class CustomTitle extends RelativeLayout {

    private IconTextView titleLeft;
    private IconTextView titleRight;
    private IconTextView titleCenter;

    private TitleClickListener titleClickListener;

    public void setTitleClickListener(TitleClickListener titleClickListener) {
        this.titleClickListener = titleClickListener;
    }

    public CustomTitle(Context context) {
        super(context);
        initView(context);
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.lay_custom_title, this);
        titleLeft = (IconTextView) findViewById(R.id.titleLeft);
        titleCenter = (IconTextView) findViewById(R.id.titleCenter);
        titleRight = (IconTextView) findViewById(R.id.titleRight);
    }

    public void setLeftTitle(String titleText, boolean clickable) {
        titleLeft.setText(titleText);
        if (clickable) {
            setLeftClickListener();
        }
    }

    public void setLeftTitle(int titleRes, boolean clickable) {
        titleLeft.setText(titleRes);
        if (clickable) {
            setLeftClickListener();
        }
    }

    public void setRightTitle(String titleText, boolean clickable) {
        titleRight.setText(titleText);
        if (clickable) {
            setRightClickListener();
        }
    }

    public void setRightTitle(int titleRes, boolean clickable) {
        titleRight.setText(titleRes);
        if (clickable) {
            setRightClickListener();
        }
    }

    public void setCenterTitle(String titleText) {
        titleCenter.setText(titleText);
    }

    public void setLeftClickListener() {
        titleLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleClickListener != null) {
                    titleClickListener.titleLeftClick();
                }
            }
        });
    }

    public void setRightClickListener() {
        titleRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleClickListener != null) {
                    titleClickListener.titleRightClick();
                }
            }
        });
    }

    public interface TitleClickListener {
        void titleLeftClick();

        void titleRightClick();
    }
}
