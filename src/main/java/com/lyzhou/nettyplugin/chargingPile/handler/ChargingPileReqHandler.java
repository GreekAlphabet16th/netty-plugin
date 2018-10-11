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
        System.out.println(ByteUtil.bytes2HexStr(array));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

}
