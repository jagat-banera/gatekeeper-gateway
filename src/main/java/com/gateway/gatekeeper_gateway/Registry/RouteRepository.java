package com.gateway.gatekeeper_gateway.Registry;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RouteRepository {

    private final Map<String,String> routes = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){

        try{
            // Load the routes
        }
        catch(Exception e){
            new RuntimeException("Route Loading Unsuccesfull");
        }
        finally {
            System.out.println("Routes Loaded Successfully in Memmory");
        }

    }

    public String FindByEndpoint(String endpoint){
        return routes.get(endpoint);
    }



}
