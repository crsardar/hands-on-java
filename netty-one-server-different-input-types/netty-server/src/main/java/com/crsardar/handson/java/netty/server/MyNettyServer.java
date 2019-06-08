package com.crsardar.handson.java.netty.server;

import com.crsardar.handson.java.protobuff.client.ClientMsgContainer;
import com.crsardar.handson.java.protobuff.client.ClientMsgContainerTwo;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MyNettyServer {

    private final int port;

    public MyNettyServer(int port) {

        this.port = port;
    }

    private void start() {

        ChannelFactory channelFactory =
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

        ServerBootstrap serverBootstrap = new ServerBootstrap(channelFactory);
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {

                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("ProtobufVarint32FrameDecoder", new ProtobufVarint32FrameDecoder());

                pipeline.addLast("ProtobufDecoder", new ProtobufDecoder(ClientMsgContainer.ClientMsg.getDefaultInstance()));

                pipeline.addLast("ProtobufDecoderTwo", new ProtobufDecoder(ClientMsgContainerTwo.ClientMsgTwo.getDefaultInstance()));

                pipeline.addLast("ProtobufVarint32LengthFieldPrepender", new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast("ProtobufEncoder", new ProtobufEncoder());

                pipeline.addLast("ChannedHandlerOne", new ChannedHandlerOne());

                return pipeline;
            }
        });

        serverBootstrap.setOption("tcpNoDelay", true);
        serverBootstrap.setOption("child.tcpNoDelay", true);
        serverBootstrap.setOption("child.keepAlive", true);

        serverBootstrap.bind(new InetSocketAddress(port));

    }

    private class ChannedHandlerOne extends SimpleChannelUpstreamHandler{

        @Override
        public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {

            if (e instanceof UpstreamMessageEvent){

                Object msg = ((UpstreamMessageEvent) e).getMessage();
                System.out.println("Server : Received : " + msg.getClass() + " = " + msg.toString());
            }

            super.handleUpstream(ctx, e);
        }
    }

    public static void main(String... arg) {

        MyNettyServer myNettyServer = new MyNettyServer(8080);
        System.out.println("Server : main() : Thread = " + Thread.currentThread().getId());
        myNettyServer.start();
    }
}
