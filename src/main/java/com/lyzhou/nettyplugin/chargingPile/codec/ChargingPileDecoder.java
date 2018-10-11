package com.lyzhou.nettyplugin.chargingPile.codec;

import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileStatus;
import com.lyzhou.nettyplugin.chargingPile.domain.enums.ChargingPileEnum;
import com.lyzhou.nettyplugin.chargingPile.domain.enums.ChargingPileStatusEnum;
import com.lyzhou.nettyplugin.chargingPile.domain.resp.StartChargingResp;
import com.lyzhou.nettyplugin.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChargingPileDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChargingPileDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        //头信息：统一为0xade635de
        if (in.readableBytes() < 4 || in.readInt() != 0xade635de) {
            LOGGER.info("ChargingPile Header is error");
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("parse ChargingPileMessage... try parse...");
        }
        list.add(getChargingPileMessage(ctx, in));
    }

    /**
     * 消息体判断
     * */
    private ChargingPileMessage getChargingPileMessage(ChannelHandlerContext ctx, ByteBuf buf){
        ChargingPileMessage message = new ChargingPileMessage();
        message.setType(buf.readByte());
        message.setBodyLength(buf.readShort());
        //终端号？十进制：十六进制
        byte[] deviceID = new byte[16];
        buf.readBytes(deviceID);
        //消息体类型
        byte type = buf.getByte(4);
        switch (ChargingPileEnum.getByValue(type)){
            case STATUS:{
                ChargingPileStatus status = new ChargingPileStatus();
                //终端号：十六进制字符串
                status.setDeviceID(ByteUtil.bytes2HexStr(deviceID));
                //当前桩状态
                status.setStatus(ChargingPileStatusEnum.getByValue(buf.readShort()));
                //+++++++++充电信息++++++++
                //电压/10
                status.setVoltage(Float.toString((float) buf.readShort() / 10));
                //电流/1000
                status.setElectricity(Float.toString((float) buf.readShort() / 1000));
                //功率/10000
                status.setPower(Float.toString((float) buf.readShort() / 10000));
                //本次充电流水号
                long serialNum = buf.readLong();
                status.setSerialNum(Long.toHexString(serialNum));
                //充电时长
                StringBuilder time = new StringBuilder(buf.readByte() + "时" + buf.readByte() + "分" + buf.readByte() + "秒");
                status.setTime(time.toString());
                //充电电量/100
                status.setElectricQuantity(Float.toString((float) buf.readShort() / 100));
                //充电金额/100
                status.setAmount(Float.toString((float) buf.readShort() / 100));
                buf.clear();
                message.setBody(status);
                break;
            }
            case START:
                StartChargingResp start = new StartChargingResp();
                //终端号：十六进制字符串
                start.setDeviceID((ByteUtil.bytes2HexStr(deviceID)));
                start.setResult(buf.readByte() == 0x01 ? "true":"false" );
                message.setBody(start);
        }
        return message;
    }
}
