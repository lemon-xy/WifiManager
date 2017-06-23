package pear.lemon.wifi.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pear.lemon.wifi.R;
import pear.lemon.wifi.entity.ConfigModel;
import pear.lemon.wifi.entity.WifiModel;
import pear.lemon.wifi.listener.DataActionListener;
import pear.lemon.wifi.listener.OnItemClickListener;
import pear.lemon.wifi.widget.IconTextView;

/**
 * Wifi适配器
 */

public class ConfigAdapter extends RecyclerView.Adapter {

    private List<ConfigModel> dataList;

    private LayoutInflater layoutInflater;

    private boolean showCode;//是否显示密码

    public void setShowCode(boolean showCode) {
        this.showCode = showCode;
    }

    public ConfigAdapter(Context context, List<ConfigModel> dataList) {
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_config, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CustomViewHolder) {
            CustomViewHolder viewHolder = (CustomViewHolder) holder;
            final ConfigModel data = dataList.get(position);
            viewHolder.tvWifiName.setText(data.getWifiName());
            viewHolder.tvWifiCode.setText(data.getWifiCode());
            if (showCode) {
                viewHolder.tvWifiCode.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvWifiCode.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvWifiName)
        TextView tvWifiName;
        @BindView(R.id.tvWifiCode)
        TextView tvWifiCode;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
