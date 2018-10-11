package com.lyzhou.nettyplugin.chargingPile.server;

import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.lyzhou.nettyplugin.chargingPile.domain.req.StartChargingReq;

/**
 * 消息工厂类
 * @author zhouliyu
 * */
public class MessageFactory {

    public static ChargingPileMessage instance(ChargingPileMessage message){

        byte[] deviceID = {0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
        if(message.getBody() instanceof StartChargingReq ){
                message.setType((byte) 0x10);
                message.setBodyLength((short) 0x0013);
                StartChargingReq start = new StartChargingReq();
                start.setDeviceID(deviceID);
                start.setMode((byte)0x01);
                start.setPrepayment((short)0x0001);
                message.setBody(start);
        }
        return message;
    }
}
