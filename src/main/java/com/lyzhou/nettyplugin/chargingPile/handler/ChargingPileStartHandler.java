package com.lyzhou.nettyplugin.chargingPile.handler;

import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.lyzhou.nettyplugin.waterGauge.utils.ByteUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 充电启动命令
 * @author zhouliyu
 * */
public class ChargingPileStartHandler extends SimpleChannelInboundHandler<ChargingPileMessage> {

    //充电启动消息
    private byte[] reqMessage;

    public ChargingPileStartHandler(byte[] reqMessage) {
        this.reqMessage = reqMessage;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(reqMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChargingPileMessage message) throws Exception {
        if(message.getBody() instanceof byte[]){
            byte[] body = (byte[]) message.getBody();
            byte[] deviceID = Arrays.copyOfRange(body,0,16);
            Map<String, Object> map = new HashMap<>();
            map.put("设备编号", ByteUtil.bytes2HexStr(deviceID));
            if(body[16] == 0x01){
                map.put("充电结果", "启动成功");
            }else {
                map.put("充电结果", "启动失败");
            }
            message.setBody(map);
        }
        System.out.println(message.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
