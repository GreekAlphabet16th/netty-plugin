package com.lyzhou.nettyplugin.waterGauge.domain;

import java.io.Serializable;

/**
 * 有线水位尺
 * @author zhouliyu
 * */
public class WaterGaugeMessage implements Serializable {

    private static final long serialVersionUID = -1293526299691152141L;

    public static final short MESSAGE_LENGTH = 10;
    //地址码
    private short addressCode;
    //功能码
    private short functionCode;
    //水位
    private String waterLevel;
    //水温
    private String waterTemperature;

    public short getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(short addressCode) {
        this.addressCode = addressCode;
    }

    public short getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(short functionCode) {
        this.functionCode = functionCode;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(String waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    @Override
    public String toString() {
        return "WaterGaugeMessage{" +
                "addressCode=" + addressCode +
                ", functionCode=" + functionCode +
                ", waterLevel='" + waterLevel + '\'' +
                ", waterTemperature='" + waterTemperature + '\'' +
                '}';
    }
}
