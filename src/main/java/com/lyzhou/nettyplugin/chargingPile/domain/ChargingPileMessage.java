package com.lyzhou.nettyplugin.chargingPile.domain;

import java.io.Serializable;

public class ChargingPileMessage<T> implements Serializable {
    private static final long serialVersionUID = 5858325998083370402L;
    //消息起始头
    private int head = 0xade635de;
    //消息类型
    private byte type;
    //消息体长度
    private short bodyLength;
    //消息体
    private T body;

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public short getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(short bodyLength) {
        this.bodyLength = bodyLength;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ChargingPileMessage{" +
                "head=" + head +
                ", type=" + type +
                ", bodyLength=" + bodyLength +
                ", body=" + body +
                '}';
    }
}

