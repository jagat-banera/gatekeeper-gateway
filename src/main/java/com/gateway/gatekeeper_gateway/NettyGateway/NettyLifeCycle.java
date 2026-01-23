package com.gateway.gatekeeper_gateway.NettyGateway;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class NettyLifeCycle implements SmartLifecycle {

    private final NettyServer nettyServer ;
    private boolean running = false ;

    public NettyLifeCycle(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    public void start() {
        // Start the NettyServer
        nettyServer.start();
        running = true ;
    }

    @Override
    public void stop() {
        // Stop the server
        nettyServer.stop();
        running = false ;
    }

    @Override
    public boolean isRunning() {
        return running;
    }


}
