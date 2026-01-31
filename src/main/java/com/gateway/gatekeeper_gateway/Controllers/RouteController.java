package com.gateway.gatekeeper_gateway.Controllers;


import com.gateway.gatekeeper_gateway.DTOs.ActiveRouteView;
import com.gateway.gatekeeper_gateway.Services.RouteLoader;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
public class RouteController {

    private final RouteLoader routeLoader ;

    public RouteController(RouteLoader routeLoader) {
        this.routeLoader = routeLoader;
    }

    @PostMapping("/add-route")
    public ResponseEntity<String> addRoute(@RequestBody ActiveRouteView activeRouteView){

        // Add the DTO
        if(routeLoader.addRoute(activeRouteView)){
            return ResponseEntity.ok("Route Added Successfully");
        }

        return ResponseEntity.internalServerError().body("Error Occurred at Gateway Service");

    }

}
