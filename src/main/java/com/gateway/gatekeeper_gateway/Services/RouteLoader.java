package com.gateway.gatekeeper_gateway.Services;

import com.gateway.gatekeeper_gateway.DTOs.ActiveRouteView;
import com.gateway.gatekeeper_gateway.Registry.RouteRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RouteLoader {

    private final RouteRepository routeRepository ;
    private final RouteRegistryClient routeRegistryClient ;



    public RouteLoader(RouteRepository routeRepository, RouteRegistryClient routeRegistryClient) {
        this.routeRepository = routeRepository;
        this.routeRegistryClient = routeRegistryClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        System.out.println("Loading Routes from Registry ...");

        try {

            // To Fetch all the Routes
            List<ActiveRouteView> routes = routeRegistryClient.fetchRoutes();

            // To Load all the routes in Memory
            routeRepository.loadRoutes(routes);

            System.out.println("Routes Loaded Successfully");

        }

        catch (Exception e){
            throw new IllegalStateException(
                    "Route Loading Unsuccesfull. Aborting Startup",
                    e
            );
        }
    }



}
