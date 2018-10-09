package com.lyzhou.nettyplugin.chargingPile.domain;

import java.io.Serializable;

public class ChargingPileSubscribe implements Serializable {
    private static final long serialVersionUID = -6277359440151358156L;

    private String deviceID;

    private String result;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ChargingPileSubscribe{" +
                "deviceID='" + deviceID + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
