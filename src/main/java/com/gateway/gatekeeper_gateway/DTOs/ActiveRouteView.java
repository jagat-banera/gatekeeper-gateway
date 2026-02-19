package com.gateway.gatekeeper_gateway.DTOs;

public record ActiveRouteView(
        RouteKey routeKey,
        Route route
) {

    @Override
    public RouteKey routeKey() {
        return routeKey;
    }

    @Override
    public Route route() {
        return route;
    }
}
