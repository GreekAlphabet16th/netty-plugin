package com.lyzhou.nettyplugin.waterGauge.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class WaterGaugeEncoder extends MessageToByteEncoder<byte[]> {


    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] bytes, ByteBuf out) throws Exception {

        System.out.println("WaterGaugeEncoder encode msg......");
        out.writeBytes(bytes);
    }
}
