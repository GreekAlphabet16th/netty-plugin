package com.lyzhou.nettyplugin.chargingPile.domain.enums;

public enum ChargingPileEnum {
    STATUS((byte) 0x0b),//充电状态
    START((byte)0x0c),//充电开始
    STOP((byte) 0x0d),//充电结束
    SUBSCRIBE((byte) 0x21),//充电预约
    CANCEL((byte) 0x23);//充电取消

    private byte value;

    ChargingPileEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ChargingPileEnum getByValue(byte value){
        for(ChargingPileEnum chargingPile : values()){
            if(chargingPile.getValue() == value){
                return chargingPile;
            }
        }
        return null;
    }
}
