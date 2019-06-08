package com.crsardar.handson.java.netty.client;

import com.crsardar.handson.java.protobuff.client.ClientMsgContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ClientObjectHandler extends SimpleChannelInboundHandler<Object> {

    int count = 0;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // Because All Calls On SAME Thread
        new Thread(() -> {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Please type a message and Enter - ");

            while (true) {

                String string = scanner.nextLine();

                if (count % 2 == 1) {

                    ClientMsgContainer.ClientMsg.Builder msgBuilder = ClientMsgContainer.ClientMsg.newBuilder();
                    msgBuilder.setClientName("MyNettyClient");
                    msgBuilder.setTime(LocalDateTime.now().toString());
                    msgBuilder.setMessage(string);

                    ClientMsgContainer.ClientMsg clientMsg = msgBuilder.build();

                    ctx.writeAndFlush(clientMsg);

                } else {

                    ctx.writeAndFlush(string);
                }

                count++;
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("---> " + this.getClass().getSimpleName() + "  Reads  : " + msg.getClass().getSimpleName()
                + " = " + msg.toString());
    }

}
