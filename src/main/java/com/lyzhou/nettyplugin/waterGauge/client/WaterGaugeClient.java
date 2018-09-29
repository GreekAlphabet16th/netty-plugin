package com.lyzhou.nettyplugin.waterGauge.client;

import com.lyzhou.nettyplugin.waterGauge.codec.WaterGaugeDecoder;
import com.lyzhou.nettyplugin.waterGauge.codec.WaterGaugeEncoder;
import com.lyzhou.nettyplugin.waterGauge.handelr.WaterGaugeReqHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WaterGaugeClient {


    public void connect(int port, String host) throws InterruptedException {
        //请求信息
        byte[] reqMessage = {0x01,0x03,0x00,0x00,0x00,0x01,(byte) 0x84,0x0A};
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new WaterGaugeEncoder());
                            socketChannel.pipeline().addLast(new WaterGaugeDecoder());
                            socketChannel.pipeline().addLast(new WaterGaugeReqHandler(reqMessage));
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
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new WaterGaugeClient().connect(port, "10.70.1.120");
    }
}
