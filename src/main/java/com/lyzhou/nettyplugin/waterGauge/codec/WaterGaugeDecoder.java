package com.lyzhou.nettyplugin.waterGauge.codec;

import com.lyzhou.nettyplugin.waterGauge.domain.WaterGaugeMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WaterGaugeDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WaterGaugeDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf out, List<Object> list) throws Exception {
        if(out.readableBytes() < WaterGaugeMessage.MESSAGE_LENGTH){
            LOGGER.debug("ByteBuf is less than WaterGaugeMessage length");
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("parse StaffGaugeMessage... try parse...");
        }

        WaterGaugeMessage message = new WaterGaugeMessage();
        message.setAddressCode(out.readByte());
        message.setFunctionCode(out.readByte());
        StringBuilder waterLevel = new StringBuilder();
        StringBuilder waterTemperature = new StringBuilder();
        waterLevel.append(out.readByte() + "." + out.readByte());
        message.setWaterLevel(waterLevel.toString());
        if(out.readByte() == 0){
            waterTemperature.append("+" + out.readByte() + "." + out.readByte());
        }else {
            waterTemperature.append("-" + out.readByte() + "." + out.readByte());
        }
        message.setWaterTemperature(waterTemperature.toString());
        out.clear(); //out readIndex 清空
        list.add(message);
    }
}
