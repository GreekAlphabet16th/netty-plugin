package com.lyzhou.nettyplugin.chargingPile.handler;

import com.lyzhou.nettyplugin.waterGauge.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class ChargingPileStartRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] array = new byte[((ByteBuf) msg).readableBytes()];
        buf.readBytes(array);
        System.out.println("Server receive......" + ByteUtil.bytes2HexStr(array));
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
        byte[] message = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde, 0x0d, 0x00, 0x20,
                0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x01,
                0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
                0x01, 0x03, 0x11,
                0x38, 0x30, 0x30, 0x30};
        return message;
    }
}
