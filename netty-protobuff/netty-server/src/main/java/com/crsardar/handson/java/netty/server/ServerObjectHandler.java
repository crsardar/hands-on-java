package com.crsardar.handson.java.netty.server;

import com.crsardar.handson.java.protobuff.server.ServerResponseContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class ServerObjectHandler extends SimpleChannelInboundHandler<Object> {

    int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("---> " + this.getClass().getSimpleName() + "  Reads : " + msg.getClass().getSimpleName()
                + " = " + msg.toString());

        if (count % 2 == 1) {

            ServerResponseContainer.ServerResponse.Builder builder = ServerResponseContainer.ServerResponse.newBuilder();
            builder.setTime(LocalDateTime.now().toString());
            builder.setServerName("MyNettyServer");
            builder.setMessage("Never Say Never!!");
            ServerResponseContainer.ServerResponse serverResponse = builder.build();

            ctx.writeAndFlush(serverResponse);

        } else {

            ctx.writeAndFlush("This is just a String from Server");

        }

        count++;
    }
}
