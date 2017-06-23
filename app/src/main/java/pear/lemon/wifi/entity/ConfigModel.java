package pear.lemon.wifi.entity;

/**
 * Wifi实体类
 */

public class ConfigModel {
    private String wifiName;//ssid
    private String wifiCode;//code

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiCode() {
        return wifiCode;
    }

    public void setWifiCode(String wifiCode) {
        this.wifiCode = wifiCode;
    }
}
