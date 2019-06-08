package client;

import com.silver.hdp5signals.mdcodegen.signals.HDP5Signals;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class MyNettyClient {

    private final String serverAdd;
    private final int port;

    public MyNettyClient(String serverAdd, int port) {

        this.port = port;
        this.serverAdd = serverAdd;
    }


    private void start() {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            protected void initChannel(SocketChannel socketChannel) throws Exception {

                ChannelPipeline pipeline = socketChannel.pipeline();

                // Proto-Buff Stubs

                // This code is working for ProtocallBuffer with Netty
//                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new MyProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(HDP5Signals.HDPMsg.getDefaultInstance()));

                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());

                pipeline.addLast(new ClientHandler());


                // Testing Object Encoder-Decoder
                /*
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ClientObjectHandler());
                */
                //System.out.println("Client : Pipeline : Thread = " + Thread.currentThread().getId());
            }
        });

        try {

            ChannelFuture channelFuture = bootstrap.connect(serverAdd, port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... arg) {

        MyNettyClient myNettyClient = new MyNettyClient("10.74.34.165", 9191);

        System.out.println("Client : main() : Thread = " + Thread.currentThread().getId());

        myNettyClient.start();

    }
}
