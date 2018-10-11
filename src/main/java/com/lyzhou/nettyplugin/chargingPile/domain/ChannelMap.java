package com.lyzhou.nettyplugin.chargingPile.domain;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通道管理
 * @author zhouliyu
 * */
public class ChannelMap {
    //存储通道
    private static Map<String, Channel> map = new ConcurrentHashMap<>();

    public static Map<String, Channel> getMap() {
        return map;
    }

    public static void setMap(Map<String, Channel> map) {
        ChannelMap.map = map;
    }

    /**
     * IP获取
     * */
    public static String getIpStr(ChannelHandlerContext ctx){
        String socket = ctx.channel().remoteAddress().toString();
        int length = socket.indexOf(":");
        String ip = socket.substring(1, length);
        return ip;
    }

    /**
     * 连接信息
     * */
    public static String getRemoteAddress(ChannelHandlerContext ctx){
        String socket = ctx.channel().remoteAddress().toString();
        return socket;
    }
}
