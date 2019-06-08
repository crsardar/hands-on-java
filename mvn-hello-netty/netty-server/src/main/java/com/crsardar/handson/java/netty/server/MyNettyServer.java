package com.crsardar.handson.java.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyServer {

    private final int port;

    public MyNettyServer(int port) {

        this.port = port;
    }

    private void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);

        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            protected void initChannel(SocketChannel socketChannel) throws Exception {

                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new ServerHandler());
            }
        });

        try {

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {

            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            e.printStackTrace();
        }
    }

    public static void main(String... arg) {

        MyNettyServer myNettyServer = new MyNettyServer(8080);
        myNettyServer.start();
    }
}
