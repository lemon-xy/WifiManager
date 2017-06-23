package pear.lemon.wifi.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import pear.lemon.wifi.R;
import pear.lemon.wifi.base.BasicActivity;
import pear.lemon.wifi.widget.CustomTitle;

/**
 * 密码原文
 */

public class RawActivity extends BasicActivity {

    @BindView(R.id.layTitle)
    CustomTitle layTitle;

    @BindView(R.id.dataContent)
    TextView dataContent;

    @Override
    protected int getLayoutId() {
        return R.layout.aty_raw;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        layTitle.setCenterTitle("密码原文");
        layTitle.setLeftTitle(R.string.icon_back, true);
        layTitle.setTitleClickListener(new CustomTitle.TitleClickListener() {
            @Override
            public void titleLeftClick() {
                onBackPressed();
            }

            @Override
            public void titleRightClick() {
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String wifiConfig = bundle.getString("wifiConfig");
            dataContent.setText(wifiConfig);
        }
    }

    @Override
    protected void widgetClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
