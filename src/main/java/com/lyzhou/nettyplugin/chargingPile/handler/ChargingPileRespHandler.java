package com.lyzhou.nettyplugin.chargingPile.handler;

import com.lyzhou.nettyplugin.chargingPile.domain.ChannelMap;
import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.lyzhou.nettyplugin.chargingPile.domain.enums.ChargingPileEnum;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ChargingPileRespHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargingPileRespHandler.class);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("设备地址: " + ChannelMap.getRemoteAddress(ctx));
        ChannelMap.getMap().put(ChannelMap.getIpStr(ctx), ctx.channel());
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
        try {
            switch (ChargingPileEnum.getByValue(message.getType())){
                case STATUS:
                    LOGGER.info("服务器充电桩状态响应......" + message);
                    ctx.writeAndFlush(buildMessage());
                    break;
                case START:
                    LOGGER.info("服务器充电桩启动响应......" + message);
                    break;
            }
        } finally {
            ReferenceCountUtil.release(message);
        }

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
