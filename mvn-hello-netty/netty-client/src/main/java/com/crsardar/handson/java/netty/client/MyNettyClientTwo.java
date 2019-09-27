package com.crsardar.handson.java.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyNettyClientTwo
{

    private final String serverAdd;
    private final int port;

    public MyNettyClientTwo(String serverAdd, int port) {

        this.port = port;
        this.serverAdd = serverAdd;
    }


    private void start() {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            protected void initChannel(SocketChannel socketChannel) throws Exception {

                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new ClientHandler());
            }
        });

        try {

            ChannelFuture channelFuture = bootstrap.connect(serverAdd, port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... arg) {

        MyNettyClientTwo myNettyClient = new MyNettyClientTwo("127.0.0.1", 8080);
        myNettyClient.start();

    }
}
