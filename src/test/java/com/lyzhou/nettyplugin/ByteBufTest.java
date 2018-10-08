package com.lyzhou.nettyplugin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ByteBufTest {

    @Test
    public void heapBufTest(){
        byte[] message = {0x01,0x03,0x05,21,05,00,27,68,0x4B,(byte) 0xD9};
        ByteBuf heapBuf = Unpooled.copiedBuffer(message);
        if(heapBuf.hasArray()){
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            System.out.println("offset: " + offset + " length: " + length);
        }
    }

    @Test
    public void compositeByteBuf(){
        byte[] header = {0x01,0x03,0x05};
        byte[] body = {21,05,00,27,68,0x4B,(byte) 0xD9};
        CompositeByteBuf message = Unpooled.compositeBuffer();
        ByteBuf headerBuf = Unpooled.copiedBuffer(header);
        ByteBuf bodyBuf = Unpooled.copiedBuffer(body);
        message.addComponents(headerBuf,bodyBuf);
        for (ByteBuf buf : message) {
            System.out.println(buf.toString());
        }
    }
}
