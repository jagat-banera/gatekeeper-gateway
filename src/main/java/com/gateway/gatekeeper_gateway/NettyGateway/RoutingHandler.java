package com.gateway.gatekeeper_gateway.NettyGateway;

import com.gateway.gatekeeper_gateway.DTOs.Route;
import com.gateway.gatekeeper_gateway.DTOs.RouteKey;
import com.gateway.gatekeeper_gateway.Registry.RouteRepository;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


@ChannelHandler.Sharable
@Component
public class RoutingHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final RouteRepository routeRepository ;
    private final HttpClient httpClient ;

    public RoutingHandler(RouteRepository routeRepository, HttpClient httpClient) {
        this.routeRepository = routeRepository;
        this.httpClient = httpClient ;
    }



    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        String endpoint = request.uri().split("\\?")[0];
        HttpMethod httpMethod = request.method();

        Route route = routeRepository.FindByEndpoint(
                new RouteKey(
                        endpoint,
                        httpMethod.toString()
                )
        );

        if(route == null){
            sendError(ctx, HttpResponseStatus.NOT_FOUND, "No route found for: " + endpoint);
            return ;
        }


        proxyToTarget(ctx, request, route.targetUrl() );

    }

    private void proxyToTarget(ChannelHandlerContext ctx , FullHttpRequest request , String Target){

        try{

            URI targetURL = URI.create(Target);

            // Building the HTTP Request
            HttpRequest proxyReq = HttpRequest.newBuilder(targetURL)
                    .method(request.method().name() ,
                            request.content().isReadable() ?
                                    HttpRequest.BodyPublishers.ofByteArray(request.content().array()) :
                                    HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(30))
                    .build();



            // Execute async proxy
            httpClient.sendAsync(proxyReq, HttpResponse.BodyHandlers.ofByteArray())
                    .thenAccept(response -> {
                        FullHttpResponse nettyResponse = new DefaultFullHttpResponse(
                                HttpVersion.HTTP_1_1,
                                HttpResponseStatus.valueOf(response.statusCode()),
                                Unpooled.copiedBuffer(response.body())
                        );

                        // Copy headers
                        response.headers().map().forEach((name, values) ->
                                nettyResponse.headers().set(name, values.get(0)));

                        nettyResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,
                                nettyResponse.content().readableBytes());

                        ctx.writeAndFlush(nettyResponse)
                                .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    })
                    .exceptionally(throwable -> {
                        sendError(ctx, HttpResponseStatus.BAD_GATEWAY, throwable.getMessage());
                        return null;
                    });

        }

        catch (Exception e){
            sendError(ctx ,HttpResponseStatus.BAD_GATEWAY, e.getMessage());
        }


    }


    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status, String message) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("{\"error\":\"" + message + "\"}", CharsetUtil.UTF_8)
        );
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
