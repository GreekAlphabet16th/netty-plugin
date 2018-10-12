package com.lyzhou.nettyplugin.chargingPile.codec;

import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.lyzhou.nettyplugin.chargingPile.domain.req.StartChargingReq;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 充电桩编码器-》启动
 * */
public class StartChargingEncoder extends MessageToByteEncoder<ChargingPileMessage<StartChargingReq>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChargingPileMessage<StartChargingReq> message, ByteBuf out) throws Exception {
        out.writeInt(message.getHead());
        out.writeByte(message.getType());
        out.writeShort(message.getBodyLength());
        StartChargingReq req = message.getBody();
        out.writeBytes(req.getDeviceID());
        out.writeByte(req.getMode());
        out.writeShort(req.getPrepayment());

    }
}
