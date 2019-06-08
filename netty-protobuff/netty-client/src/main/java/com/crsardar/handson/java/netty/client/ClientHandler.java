package com.crsardar.handson.java.netty.client;

import com.crsardar.handson.java.protobuff.client.ClientMsgContainer;
import com.crsardar.handson.java.protobuff.server.ServerResponseContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ClientHandler extends SimpleChannelInboundHandler<ServerResponseContainer.ServerResponse> {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        System.out.println("Client : channelActive() : Thread = " + Thread.currentThread().getId());

        // Because All Calls On SAME Thread
        new Thread(() -> {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Please type a message and Enter - ");

            while (true) {

                String msg = scanner.nextLine();

                ClientMsgContainer.ClientMsg.Builder msgBuilder = ClientMsgContainer.ClientMsg.newBuilder();
                msgBuilder.setClientName("MyNettyClient");
                msgBuilder.setTime(LocalDateTime.now().toString());
                msgBuilder.setMessage(msg);

                ClientMsgContainer.ClientMsg clientMsg = msgBuilder.build();

                ctx.writeAndFlush(clientMsg);
            }
        }).start();

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ServerResponseContainer.ServerResponse serverResponse) throws Exception {

        System.out.println("Client : channelRead0() : Thread = " + Thread.currentThread().getId());

        System.out.println("===> Client Receives : " + serverResponse.toString());
    }
}
