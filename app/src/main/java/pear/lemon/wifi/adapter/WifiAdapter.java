package pear.lemon.wifi.adapter;

import android.content.Context;
import android.provider.ContactsContract;
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
import pear.lemon.wifi.entity.WifiModel;
import pear.lemon.wifi.listener.DataActionListener;
import pear.lemon.wifi.listener.OnItemClickListener;
import pear.lemon.wifi.widget.IconTextView;

/**
 * Wifi适配器
 */

public class WifiAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<WifiModel> dataList;

    private LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    private DataActionListener dataActionListener;

    public WifiAdapter(Context context, List<WifiModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDataActionListener(DataActionListener dataActionListener) {
        this.dataActionListener = dataActionListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_wifi, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CustomViewHolder) {
            CustomViewHolder viewHolder = (CustomViewHolder) holder;
            final WifiModel data = dataList.get(position);
            viewHolder.tvWifiName.setText(data.getWifiName());
            if (data.isShowDetail()) {
                viewHolder.ivDetail.setText(R.string.icon_up);
                viewHolder.tvWifiDetail.setVisibility(View.VISIBLE);
                viewHolder.tvWifiDetail.setText(data.getWifiDetail());
            } else {
                viewHolder.ivDetail.setText(R.string.icon_down);
                viewHolder.tvWifiDetail.setVisibility(View.GONE);
            }
            if (data.getWifiType() != 0) {
                viewHolder.ivNeedCode.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivNeedCode.setVisibility(View.INVISIBLE);
            }
            viewHolder.ivDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataActionListener != null) {
                        dataActionListener.onShow(position);
                    }
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            if (data.isConnect()) {
                viewHolder.ivIntensity.setTextColor(ContextCompat.getColor(context, R.color.font_green));
            } else {
                viewHolder.ivIntensity.setTextColor(ContextCompat.getColor(context, R.color.font_text));
            }
            switch (data.getIntensity()) {
                case 0:
                    viewHolder.ivIntensity.setText(R.string.icon_signal_off);
                    break;
                case 1:
                    viewHolder.ivIntensity.setText(R.string.icon_signal_one);
                    break;
                case 2:
                    viewHolder.ivIntensity.setText(R.string.icon_signal_two);
                    break;
                case 3:
                    viewHolder.ivIntensity.setText(R.string.icon_signal_three);
                    break;
                case 4:
                    viewHolder.ivIntensity.setText(R.string.icon_signal_four);
                    break;
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
        @BindView(R.id.tvWifiDetail)
        TextView tvWifiDetail;
        @BindView(R.id.ivIntensity)
        IconTextView ivIntensity;
        @BindView(R.id.ivNeedCode)
        IconTextView ivNeedCode;
        @BindView(R.id.ivDetail)
        IconTextView ivDetail;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
