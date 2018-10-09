package com.lyzhou.nettyplugin.chargingPile.domain;

import java.io.Serializable;

public class ChargingPileStop implements Serializable {
    private static final long serialVersionUID = -6277359440151358156L;

    private String deviceID;

    private String result;

    //消费信息:充电流水号
    private String serialNum;
    //充电时长
    private String time;
    //充电电量
    private String electricQuantity;
    //充电金额
    private String amount;

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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(String electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ChargingPileStop{" +
                "deviceID='" + deviceID + '\'' +
                ", result='" + result + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", time='" + time + '\'' +
                ", electricQuantity='" + electricQuantity + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
