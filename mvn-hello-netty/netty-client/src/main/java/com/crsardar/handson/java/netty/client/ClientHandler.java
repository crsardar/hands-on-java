package com.crsardar.handson.java.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        // Because All Calls On SAME Thread
        new Thread(() -> {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Please type a message and Enter - ");

            while (true) {

                String msg = scanner.nextLine();
                ByteBuf byteBuf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);

                ctx.writeAndFlush(byteBuf);
            }
        }).start();

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf inBuffer = (ByteBuf) msg;

        String received = inBuffer.toString(CharsetUtil.UTF_8);

        System.out.println("===> Client Receives : " + received);
    }
}
