package pear.lemon.wifi.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import pear.lemon.wifi.R;
import pear.lemon.wifi.adapter.ConfigAdapter;
import pear.lemon.wifi.base.BasicActivity;
import pear.lemon.wifi.entity.ConfigModel;
import pear.lemon.wifi.util.ToastUtil;
import pear.lemon.wifi.widget.CustomTitle;

/**
 * 获取root权限--wifi列表
 */

public class SafeActivity extends BasicActivity {
    @BindView(R.id.layTitle)
    CustomTitle layTitle;

    @BindView(R.id.dataView)
    RecyclerView dataView;

    @BindView(R.id.dataEmpty)
    TextView dataEmpty;

    private ConfigAdapter dataAdapter;//wifi列表适配器

    private List<ConfigModel> dataList;//wifi列表显示

    private StringBuffer wifiConfig = new StringBuffer();

    @Override
    protected int getLayoutId() {
        return R.layout.aty_safe;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        showRootDialog();
    }

    private void initView() {
        layTitle.setCenterTitle("wifi密码");
        layTitle.setLeftTitle(R.string.icon_back, true);
        layTitle.setRightTitle(R.string.icon_content, true);
        layTitle.setTitleClickListener(new CustomTitle.TitleClickListener() {
            @Override
            public void titleLeftClick() {
                onBackPressed();
            }

            @Override
            public void titleRightClick() {
                Intent intent = new Intent(activity, RawActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("wifiConfig", wifiConfig.toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        dataAdapter = new ConfigAdapter(activity, dataList = new ArrayList<>());
        dataAdapter.setShowCode(true);
        dataView.setLayoutManager(new LinearLayoutManager(activity));
        dataView.setAdapter(dataAdapter);
    }

    /**
     * 获取root对话框
     */
    private void showRootDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("同意获取ROOT权限读取密码?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getRootSafe();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 获取root权限
     */
    private void getRootSafe() {
        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream
                    .writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConfig.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<ConfigModel> tempList = new ArrayList<>();

        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConfig.toString());
        while (networkMatcher.find()) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);

            if (ssidMatcher.find()) {
                ConfigModel model = new ConfigModel();
                model.setWifiName(ssidMatcher.group(1));
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find()) {
                    model.setWifiCode(pskMatcher.group(1));
                } else {
                    model.setWifiCode("无密码");
                }
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
