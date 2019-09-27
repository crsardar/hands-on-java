package com.crsardar.handson.java.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler extends ChannelInboundHandlerAdapter
{

    private static List<Channel> channelList = new ArrayList<>();

    private static boolean flipFlop = false;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception
    {
        Channel channel = ctx.channel();

        System.out.println("--->>> channelRegistered : channel = " + channel);

        channelList.add(channel);

        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {

        ByteBuf inBuffer = (ByteBuf) msg;

        String received = inBuffer.toString(CharsetUtil.UTF_8);

        System.out.println("---> Server Receives : " + received);

        if (channelList.size() >= 2)
        {
            Channel channel;
            if (flipFlop)
            {
                channel = channelList.get(0);
            }
            else
            {
                channel = channelList.get(1);
            }

            channel.writeAndFlush(
                    Unpooled.copiedBuffer("'" + received +  "' - It was received at " + LocalDateTime.now().toString(),
                            CharsetUtil.UTF_8));

            flipFlop = !flipFlop;
        }
        else
        {

            ctx.writeAndFlush(
                    Unpooled.copiedBuffer("It was received at " + LocalDateTime.now().toString(),
                            CharsetUtil.UTF_8));
        }
    }

}
