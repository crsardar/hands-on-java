package com.crsardar.handson.java.netty.server;

import com.crsardar.handson.java.protobuff.client.ClientMsgContainer;
import com.crsardar.handson.java.protobuff.server.ServerResponseContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class ServerHandler extends SimpleChannelInboundHandler<ClientMsgContainer.ClientMsg> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ClientMsgContainer.ClientMsg clientMsg) throws Exception {


        System.out.println("Server : channelRead0() : Thread = " + Thread.currentThread().getId());

        System.out.println("---> Server Receives : " + clientMsg.toString());

        ServerResponseContainer.ServerResponse.Builder builder = ServerResponseContainer.ServerResponse.newBuilder();
        builder.setTime(LocalDateTime.now().toString());
        builder.setServerName("MyNettyServer");
        builder.setMessage(clientMsg.getMessage().toUpperCase());
        ServerResponseContainer.ServerResponse serverResponse = builder.build();

        ctx.writeAndFlush(serverResponse);

    }

}
