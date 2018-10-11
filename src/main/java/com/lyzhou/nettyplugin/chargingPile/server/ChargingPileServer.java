package com.lyzhou.nettyplugin.chargingPile.server;

import com.lyzhou.nettyplugin.chargingPile.codec.ChargingPileDecoder;
import com.lyzhou.nettyplugin.chargingPile.codec.ChargingPileEncoder;
import com.lyzhou.nettyplugin.chargingPile.codec.StartChargingEncoder;
import com.lyzhou.nettyplugin.chargingPile.handler.ChargingPileRespHandler;
import com.lyzhou.nettyplugin.chargingPile.handler.StartChargingOutHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ChargingPileServer {

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChargingPileEncoder());//入站：pipeline头部出发
                            socketChannel.pipeline().addLast(new StartChargingEncoder());
                            socketChannel.pipeline().addLast(new ChargingPileDecoder());
                            socketChannel.pipeline().addLast(new ChargingPileRespHandler());
                            socketChannel.pipeline().addLast(new StartChargingOutHandler());//出站：pipeline尾部出发
                            socketChannel.pipeline().addLast(new StartChargingEncoder());
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture future = b.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int port = 20508;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new ChargingPileServer().bind(port);
    }
}
