package com.crsardar.handson.java.netty.server;

import com.crsardar.handson.java.protobuff.client.ClientMsgContainer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

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

                // Proto-Buff Stubs

                // This code is working for ProtocallBuffer with Netty
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(ClientMsgContainer.ClientMsg.getDefaultInstance()));

                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());

                pipeline.addLast(new ServerHandler());


                //---------------------------------------------------------------------------------//

                // Testing Object Encoder-Decoder
                /*
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ServerObjectHandler());
                */

                //System.out.println("Server : Pipeline : Thread = " + Thread.currentThread().getId());
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

        System.out.println("Server : main() : Thread = " + Thread.currentThread().getId());

        myNettyServer.start();
    }
}
