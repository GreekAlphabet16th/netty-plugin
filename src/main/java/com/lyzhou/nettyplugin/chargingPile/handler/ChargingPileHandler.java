package com.lyzhou.nettyplugin.chargingPile.handler;

import com.lyzhou.nettyplugin.chargingPile.domain.*;
import com.lyzhou.nettyplugin.chargingPile.domain.enums.ChargingPileEnum;
import com.lyzhou.nettyplugin.chargingPile.domain.enums.ChargingPileStatusEnum;
import com.lyzhou.nettyplugin.chargingPile.domain.resp.StartChargingResp;
import com.lyzhou.nettyplugin.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 充电桩命令
 * @author zhouliyu
 * */
@ChannelHandler.Sharable
public class ChargingPileHandler extends SimpleChannelInboundHandler<ChargingPileMessage> {
    //结束消息返回
    private static final byte[] STOP_RESP_SUCCESS = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde, 0x0e, 0x00, 0x13,
            0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x01};
    private static final byte[] STOP_RESP_ERROR = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde, 0x0e, 0x00, 0x13,
            0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x02};
    //请求消息
    private byte[] reqMessage;

    public ChargingPileHandler(byte[] reqMessage) {
        this.reqMessage = reqMessage;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(reqMessage);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChargingPileMessage message) throws Exception {
        System.out.println(getMessage(message));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    /**
     * 消息类型判断
     * @param message
     *
     * */
    private ChargingPileMessage getMessage(ChargingPileMessage message){
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes((byte[]) message.getBody());
        byte[] deviceID = new byte[16];
        buf.readBytes(deviceID);
        switch (ChargingPileEnum.getByValue(message.getType())){
            case START: {
                StartChargingResp start = new StartChargingResp();
                start.setDeviceID(ByteUtil.bytes2HexStr(deviceID));
                start.setResult(buf.readByte() == 0x01 ? "启动成功":"启动失败" );
                message.setBody(start);
                buf.clear();
                break;
            }
            case STOP: {
                ChargingPileStop stop = new ChargingPileStop();
                stop.setDeviceID(ByteUtil.bytes2HexStr(deviceID));
                stop.setResult(buf.readByte() == 0x01 ? "结束成功" : "结束失败");
                long serialNum = buf.readLong();
                stop.setSerialNum(Long.toHexString(serialNum));
                StringBuilder time = new StringBuilder(buf.readByte() + "时" + buf.readByte() + "分" + buf.readByte() + "秒");
                stop.setTime(time.toString());
                stop.setElectricQuantity(Float.toString((float) buf.readShort() / 100));
                stop.setAmount(Float.toString((float) buf.readShort() / 100));
                message.setBody(stop);
                buf.clear();
                break;
            }
            case SUBSCRIBE:{
                ChargingPileSubscribe subscribe = new ChargingPileSubscribe();
                subscribe.setDeviceID(ByteUtil.bytes2HexStr(deviceID));
                subscribe.setResult(buf.readByte() == 0x01 ? "预约成功":"预约失败" );
                message.setBody(subscribe);
                buf.clear();
                break;
            }
            case CANCEL:{
                ChargingPileCancel cancel = new ChargingPileCancel();
                cancel.setDeviceID(ByteUtil.bytes2HexStr(deviceID));
                cancel.setResult(buf.readByte() ==0x01 ? "预约取消成功":"预约取消失败" );
                message.setBody(cancel);
                buf.clear();
                break;
            }
            case STATUS: {
                ChargingPileStatus status = new ChargingPileStatus();
                status.setDeviceID(ByteUtil.bytes2HexStr(deviceID));
                status.setStatus(ChargingPileStatusEnum.getByValue(buf.readShort()));
                status.setVoltage(Float.toString((float) buf.readShort() / 10));
                status.setElectricity(Float.toString((float) buf.readShort() / 1000));
                status.setPower(Float.toString((float) buf.readShort() / 10000));
                long serialNum = buf.readLong();
                status.setSerialNum(Long.toHexString(serialNum));
                StringBuilder time = new StringBuilder(buf.readByte() + "时" + buf.readByte() + "分" + buf.readByte() + "秒");
                status.setTime(time.toString());
                status.setElectricQuantity(Float.toString((float) buf.readShort() / 100));
                status.setAmount(Float.toString((float) buf.readShort() / 100));
                message.setBody(status);
                buf.clear();
                break;
            }
            default:
                break;
        }
        return message;
    }

}
