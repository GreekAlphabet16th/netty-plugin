package com.lyzhou.nettyplugin.chargingPile.client;

import com.lyzhou.nettyplugin.chargingPile.codec.ChargingPileDecoder;
import com.lyzhou.nettyplugin.chargingPile.codec.ChargingPileEncoder;
import com.lyzhou.nettyplugin.chargingPile.handler.ChargingPileHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChargingPileClient {


    public void connect(int port, String host, byte[] reqMessage) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChargingPileEncoder());
                            socketChannel.pipeline().addLast(new ChargingPileDecoder());
                            socketChannel.pipeline().addLast(new ChargingPileHandler(reqMessage));
                        }
                    });
            //发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        //请求信息
        byte[] reqMessage = {(byte) 0xad, (byte) 0xe6, 0x35, (byte) 0xde, 0x22, 0x00, 0x10,
                0x38, 0x38, 0x38, 0x38, 0x30, 0x30, 0x30, 0x31,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new ChargingPileClient().connect(port, "10.70.1.120", reqMessage);
    }
}
