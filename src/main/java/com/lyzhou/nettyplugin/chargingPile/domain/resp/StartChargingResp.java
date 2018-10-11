package com.lyzhou.nettyplugin.chargingPile.domain.resp;

import java.io.Serializable;

public class StartChargingResp implements Serializable {

    private static final long serialVersionUID = 8429390526692475775L;

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
        return "StartChargingResp{" +
                "deviceID='" + deviceID + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
