package com.lyzhou.nettyplugin.chargingPile.codec;

import com.lyzhou.nettyplugin.chargingPile.domain.ChargingPileMessage;
import com.sun.org.apache.xpath.internal.operations.String;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChargingPileDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChargingPileDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < 4) {
            LOGGER.debug("in is less than ChargingPile Header length");
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("parse ChargingPileMessage... try parse...");
        }

        int tst = in.readableBytes();
        ChargingPileMessage message = new ChargingPileMessage();
        //消息起始头
        if(message.getHead() == in.readInt()){
            //消息类型
            message.setType(in.readByte());
            //消息体长度
            message.setBodyLength(in.readShort());
            //消息体
            short length = message.getBodyLength();
            byte[] array = new byte[length];
            in.readBytes(array);
            message.setBody(array);
        }
        list.add(message);
    }
}
