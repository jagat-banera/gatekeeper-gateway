package com.gateway.gatekeeper_gateway.Services;
import com.gateway.gatekeeper_gateway.DTOs.ActiveRouteView;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RouteRegistryClient {


    private final RestTemplate restTemplate ;

    public RouteRegistryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate ;
    }

    public List<ActiveRouteView> fetchRoutes(){
        ActiveRouteView[] routes =
                restTemplate.getForObject(
                        "http://localhost:8081/gateway/routes",
                        ActiveRouteView[].class
                );

        return routes == null
                ? List.of()
                : Arrays.asList(routes);
    }
}
