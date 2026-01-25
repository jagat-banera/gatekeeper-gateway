package com.gateway.gatekeeper_gateway.NettyGateway;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.stereotype.Component;


@Component
public class NettyChannelnitializer extends ChannelInitializer<SocketChannel> {

    private final RoutingHandler routeHandler;

    public NettyChannelnitializer(RoutingHandler routeHandler) {
        this.routeHandler = routeHandler;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new HttpServerCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(1_048_576));
        ch.pipeline().addLast(routeHandler);
    }
}
