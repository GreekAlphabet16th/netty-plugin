package com.lyzhou.nettyplugin.waterGauge.handelr;

import com.lyzhou.nettyplugin.waterGauge.domain.WaterGaugeMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class WaterGaugeReqHandler extends SimpleChannelInboundHandler<WaterGaugeMessage> {

    private byte[] reqMessage;
    private volatile ScheduledFuture<?> heartBeat;

    public WaterGaugeReqHandler(byte[] reqMessage) {
        this.reqMessage = reqMessage;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //每5秒发一次
        heartBeat = ctx.executor().scheduleAtFixedRate(
                new WaterGaugeReqHandler.heartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WaterGaugeMessage message) throws Exception {
        System.out.println("Client receive : " + message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private class heartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public heartBeatTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            System.out.println("Client send reqMessage to server");
            ctx.writeAndFlush(reqMessage);
        }
    }
}
