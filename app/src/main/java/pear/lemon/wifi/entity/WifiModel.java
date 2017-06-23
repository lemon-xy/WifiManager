package pear.lemon.wifi.entity;

/**
 * Wifi实体类
 */

public class WifiModel {
    private String wifiName;//ssid
    private String wifiDetail;//detail
    private int intensity;//信号强度
    private int wifiType;//wifi加密方式0:none1:wep2:wpa
    private boolean showDetail;//是否显示详细
    private boolean isConnect;//是否连接

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiDetail() {
        return wifiDetail;
    }

    public void setWifiDetail(String wifiDetail) {
        this.wifiDetail = wifiDetail;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public int getWifiType() {
        return wifiType;
    }

    public void setWifiType(int wifiType) {
        this.wifiType = wifiType;
    }
}
