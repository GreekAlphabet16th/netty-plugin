package com.lyzhou.nettyplugin.chargingPile.handler;

import com.lyzhou.nettyplugin.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * 充电桩命令
 * @author zhouliyu
 * */
@ChannelHandler.Sharable
public class ChargingPileReqHandler extends SimpleChannelInboundHandler<ByteBuf> {

    //请求消息
    private byte[] reqMessage;

    public ChargingPileReqHandler(byte[] reqMessage) {
        this.reqMessage = reqMessage;
    }

    /**
     * 充电桩每隔20秒发送一次信息
     * */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //利用写空闲进行心跳检测
        if(evt instanceof IdleStateEvent){
            ctx.writeAndFlush(reqMessage).addListener(
                    ChannelFutureListener.CLOSE_ON_FAILURE);
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf message) throws Exception {
        byte[] array = new byte[message.readableBytes()];
        message.readBytes(array);
        switch (message.getByte(4)){
            case 0x10:
                System.out.println("充电桩启动：" + ByteUtil.bytes2HexStr(array));
                ctx.writeAndFlush(buildMessage());
                break;
            case 0x0b:
                System.out.println("充电桩状态响应： " + ByteUtil.bytes2HexStr(array));
                break;
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private byte[] buildMessage(){
        byte[] message = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde,
                0x0c,
                0x00, 0x11,
                0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x01};
        return message;
    }

}
