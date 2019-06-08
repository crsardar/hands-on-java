package com.crsardar.handson.java.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        ByteBuf inBuffer = (ByteBuf) msg;

        String received = inBuffer.toString(CharsetUtil.UTF_8);

        System.out.println("---> Server Receives : " + received);

        ctx.writeAndFlush(Unpooled.copiedBuffer("It was received at " + LocalDateTime.now().toString(), CharsetUtil.UTF_8));

    }

}
