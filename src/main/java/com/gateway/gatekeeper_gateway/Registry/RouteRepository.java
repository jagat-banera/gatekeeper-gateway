package com.gateway.gatekeeper_gateway.Registry;

import com.gateway.gatekeeper_gateway.DTOs.ActiveRouteView;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RouteRepository {

    private final Map<String,String> routes = new ConcurrentHashMap<>();


    public String FindByEndpoint(String endpoint){
        return routes.get(endpoint);
    }


    public void loadRoutes(List<ActiveRouteView> Loadedroutes) {

        // Clear any pre-exitsing routes
        routes.clear();

        // Load the Routes Again

        Loadedroutes.forEach(route ->
                routes.put(
                        route.getEndpoint(),
                        route.getTargetUrl()
                )
        );


    }
}
