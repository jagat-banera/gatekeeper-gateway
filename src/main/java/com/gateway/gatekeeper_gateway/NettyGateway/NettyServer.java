package com.gateway.gatekeeper_gateway.NettyGateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;


@Component
public class NettyServer {

    private EventLoopGroup boss ;
    private EventLoopGroup worker ;

    private RoutingHandler routeHandler ;


    public void start(){
        try{
            boss = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
            worker = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

            ServerBootstrap bootstrap = new ServerBootstrap() ;
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyChannelnitializer(routeHandler));

            bootstrap.bind(9090).sync();

            System.out.println("Started Netty Server at - " + System.currentTimeMillis() );

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void stop(){
        if(boss != null){
            boss.shutdownGracefully();
        }

        if(worker != null){
            worker.shutdownGracefully();
        }

        System.out.println("Netty Stopped at --- " + System.currentTimeMillis());
    }



}
