package com.gateway.gatekeeper_gateway.Registry;

import com.gateway.gatekeeper_gateway.DTOs.ActiveRouteView;
import com.gateway.gatekeeper_gateway.DTOs.Route;
import com.gateway.gatekeeper_gateway.DTOs.RouteKey;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RouteRepository {

    private final Map<RouteKey, Route> routes = new ConcurrentHashMap<>();


    public Route FindByEndpoint(RouteKey key){
        return routes.get(key);
    }


    public void loadRoutes(List<ActiveRouteView> Loadedroutes) {

        // Clear any pre-exitsing routes
        routes.clear();

        // Load the Routes Again

        Loadedroutes.forEach(loadedRoute ->
                routes.put(
                        loadedRoute.routeKey(),
                        loadedRoute.route()
                )
        );


    }

    public void addRoute(ActiveRouteView activeRouteView){
        // Add a new route
        routes.put(activeRouteView.routeKey(), activeRouteView.route());
    }

    public void deleteRoute(ActiveRouteView activeRouteView){

        // Delete the existing Route
        routes.remove(activeRouteView.routeKey());
    }


}
