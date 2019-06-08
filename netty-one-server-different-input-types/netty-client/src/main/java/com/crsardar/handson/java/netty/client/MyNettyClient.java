package com.crsardar.handson.java.netty.client;

import com.crsardar.handson.java.protobuff.client.ClientMsgContainer;
import com.crsardar.handson.java.protobuff.client.ClientMsgContainerTwo;
import com.crsardar.handson.java.protobuff.server.ServerResponseContainer;
import com.crsardar.handson.java.protobuff.server.ServerResponseContainerTwo;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class MyNettyClient {

    private final String serverAdd;
    private final int port;

    public MyNettyClient(String serverAdd, int port) {

        this.port = port;
        this.serverAdd = serverAdd;
    }


    private void start() {

        ChannelFactory channelFactory =
                new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

        ClientBootstrap clientBootstrap = new ClientBootstrap(channelFactory);
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() {

                ChannelPipeline pipeline = Channels.pipeline();

                pipeline.addLast("ProtobufVarint32FrameDecoder", new ProtobufVarint32FrameDecoder());
                pipeline.addLast("ProtobufDecoder",
                        new ProtobufDecoder(ServerResponseContainer.ServerResponse.getDefaultInstance()));
                pipeline.addLast("ProtobufDecoderTwo",
                        new ProtobufDecoder(ServerResponseContainerTwo.ServerResponseTwo.getDefaultInstance()));

                pipeline.addLast("ProtobufVarint32LengthFieldPrepender", new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast("ProtobufEncoder", new ProtobufEncoder());

                pipeline.addLast("ChannedHandlerOne", new ChannedHandlerOne());

                return pipeline;
            }
        });

        clientBootstrap.setOption("tcpNoDelay", true);
        clientBootstrap.setOption("keepAlive", true);

        clientBootstrap.connect(new InetSocketAddress(serverAdd, port));


    }

    private class ChannedHandlerOne extends SimpleChannelUpstreamHandler {

        int count = 0;
        Channel channel = null;

        @Override
        public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {

            channel = ctx.getChannel();

            System.out.println(this.getClass().getSimpleName() + " # channelOpen() ");

            new Thread(() -> {

                Scanner scanner = new Scanner(System.in);

                System.out.println("Please type your message here - ");

                while (true){

                    String string = scanner.nextLine();
                    if(count%2==1){

                        ClientMsgContainer.ClientMsg.Builder builder = ClientMsgContainer.ClientMsg.newBuilder();
                        builder.setMessage("Clent Message One");

                        final ChannelFuture channelFuture = channel.write(builder.build());
                        channelFuture.syncUninterruptibly();

                    }else{

                        ClientMsgContainerTwo.ClientMsgTwo.Builder builder = ClientMsgContainerTwo.ClientMsgTwo.newBuilder();
                        builder.setGroupName("Group One");

                        final ChannelFuture channelFuture = channel.write(builder.build());
                        channelFuture.syncUninterruptibly();
                    }

                    count++;
                }

            }).start();

            super.channelOpen(ctx, e);
        }

        @Override
        public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {

            if (e instanceof UpstreamMessageEvent) {

                Object msg = ((UpstreamMessageEvent) e).getMessage();
                System.out.println(this.getClass().getSimpleName() + " : Received = " + msg.toString());
            }

            super.handleUpstream(ctx, e);
        }
    }

    public static void main(String... arg) {

        MyNettyClient myNettyClient = new MyNettyClient("localhost", 8080);

        System.out.println("Client : main() : Thread = " + Thread.currentThread().getId());

        myNettyClient.start();

    }
}
