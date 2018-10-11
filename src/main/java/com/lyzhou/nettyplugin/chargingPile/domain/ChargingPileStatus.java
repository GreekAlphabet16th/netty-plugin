package com.lyzhou.nettyplugin.chargingPile.domain;

import java.io.Serializable;

public class ChargingPileStatus implements Serializable {

    private static final long serialVersionUID = 8861760261044193720L;
    private String deviceID;

    private String status;

    //电压
    private String voltage;
    //电流
    private String electricity;
    //功率
    private String power;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
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
        return "ChargingPileStatus{" +
                "deviceID='" + deviceID + '\'' +
                ", status='" + status + '\'' +
                ", voltage='" + voltage + '\'' +
                ", electricity='" + electricity + '\'' +
                ", power='" + power + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", time='" + time + '\'' +
                ", electricQuantity='" + electricQuantity + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
