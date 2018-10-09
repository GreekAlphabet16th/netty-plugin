package com.lyzhou.nettyplugin.chargingPile.domain.enums;

public enum  ChargingPileStatusEnum {

    FREE((short)0x0000, "待机空闲中"),//空闲
    CONNECT((short)0x0001, "充电桩连接"),//连接
    USE((short)0x0002, "充电中"),//充电中
    ERROR((short)0x0004, "故障"); //故障

    private short value;
    private String name;

    ChargingPileStatusEnum(short value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public short getValue() {
        return value;
    }

    public static String getByValue(short value){
        for(ChargingPileStatusEnum chargingPileStatus : values()){
            if(chargingPileStatus.getValue() == value){
                return chargingPileStatus.getName();
            }
        }
        return null;
    }
}
