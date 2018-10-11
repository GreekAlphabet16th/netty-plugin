package com.lyzhou.nettyplugin.chargingPile.domain.req;

import java.io.Serializable;
import java.util.Arrays;

public class StartChargingReq implements Serializable {

    private static final long serialVersionUID = 8482528533338745063L;
    //终端号
    private byte[] deviceID;
    //充电方式
    private byte mode;
    //预付金额
    private short prepayment;

    public byte[] getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(byte[] deviceID) {
        this.deviceID = deviceID;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public short getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(short prepayment) {
        this.prepayment = prepayment;
    }

    @Override
    public String toString() {
        return "StartChargingReq{" +
                "deviceID=" + Arrays.toString(deviceID) +
                ", mode=" + mode +
                ", prepayment=" + prepayment +
                '}';
    }
}
