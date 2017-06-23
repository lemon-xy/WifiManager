package pear.lemon.wifi.main;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pear.lemon.wifi.R;
import pear.lemon.wifi.adapter.ConfigAdapter;
import pear.lemon.wifi.base.BasicActivity;
import pear.lemon.wifi.entity.ConfigModel;
import pear.lemon.wifi.util.StringUtil;
import pear.lemon.wifi.widget.CustomTitle;

/**
 * 连接配置过的wifi列表
 */

public class ConfigActivity extends BasicActivity {

    @BindView(R.id.layTitle)
    CustomTitle layTitle;

    @BindView(R.id.dataView)
    RecyclerView dataView;

    @BindView(R.id.dataEmpty)
    TextView dataEmpty;

    private WifiManager wifiManager;//wifi管理器

    private ConfigAdapter dataAdapter;//wifi列表适配器

    private List<ConfigModel> dataList;//wifi列表显示

    private List<WifiConfiguration> wifiConfigList;//配置好的wifi列表

    @Override
    protected int getLayoutId() {
        return R.layout.aty_config;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        getConfig();
    }

    private void initView() {
        layTitle.setCenterTitle("历史连接");
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
        wifiConfigList = new ArrayList<>();
        dataAdapter = new ConfigAdapter(activity, dataList = new ArrayList<>());
        dataView.setLayoutManager(new LinearLayoutManager(activity));
        dataView.setAdapter(dataAdapter);
    }

    private void getConfig() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiConfigList.clear();
        wifiConfigList.addAll(wifiManager.getConfiguredNetworks());

        List<ConfigModel> tempList = new ArrayList<>();
        if (wifiConfigList != null && wifiConfigList.size() > 0) {
            for (WifiConfiguration config : wifiConfigList) {
                if (StringUtil.isEmpty(config.SSID)) {
                    continue;
                }
                ConfigModel model = new ConfigModel();
                model.setWifiName(config.SSID.replaceAll("\"", ""));
                model.setWifiCode(config.preSharedKey);
                tempList.add(model);
            }
        }

        //数据倒序处理
        int size = tempList.size();
        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                dataList.add(tempList.get(i));
            }
        }
        if (dataList.size() > 0) {
            dataEmpty.setVisibility(View.GONE);
            dataView.setVisibility(View.VISIBLE);
            dataAdapter.notifyDataSetChanged();
        } else {
            dataEmpty.setVisibility(View.VISIBLE);
            dataEmpty.setText(R.string.data_empty);
            dataView.setVisibility(View.GONE);
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
