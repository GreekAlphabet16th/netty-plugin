package com.lyzhou.nettyplugin.chargingPile.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChargingPileStartRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server receive......");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private byte[] buildMessage(){
        byte[] message = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde, 0x0c, 0x00, 0x11,
                0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x02};
        return message;
    }
}
