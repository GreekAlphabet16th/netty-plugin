package com.lyzhou.nettyplugin.chargingPile.handler;

import com.lyzhou.nettyplugin.chargingPile.domain.ChannelMap;
import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.lyzhou.nettyplugin.chargingPile.domain.enums.ChargingPileEnum;
import com.lyzhou.nettyplugin.chargingPile.domain.req.StartChargingReq;
import com.lyzhou.nettyplugin.chargingPile.server.MessageFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ChargingPileRespHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargingPileRespHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("设备地址: " + ChannelMap.getRemoteAddress(ctx));
        ChannelMap.getMap().put(ChannelMap.getIpStr(ctx), ctx.channel());
        ChargingPileMessage<StartChargingReq> message = new ChargingPileMessage<>();
        StartChargingReq req = new StartChargingReq();
        message.setBody(req);
        ChannelMap.getMap().get("10.70.1.120").writeAndFlush(MessageFactory.instance(message));//ctx消息从pipeline尾部流转
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除失效channel
        ChannelMap.getMap().remove(ChannelMap.getIpStr(ctx));
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChargingPileMessage message = (ChargingPileMessage) msg;

        switch (ChargingPileEnum.getByValue(message.getType())){
            case STATUS:
                ctx.writeAndFlush(buildMessage());
        }
        LOGGER.info("Server receive......" + message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private byte[] buildMessage(){
        byte[] message = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde, 0x0b, 0x00, 0x01, 0x00};
        return message;
    }

}
