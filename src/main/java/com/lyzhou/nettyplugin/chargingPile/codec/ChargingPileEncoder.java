package com.lyzhou.nettyplugin.chargingPile.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 充电桩消息编码
 * @author zhouliyu
 * */
public class ChargingPileEncoder extends MessageToByteEncoder<byte[]> {


    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] bytes, ByteBuf byteBuf) throws Exception {
        System.out.println("ChargingPileEncoder msg......");
        byteBuf.writeBytes(bytes);
    }
}
