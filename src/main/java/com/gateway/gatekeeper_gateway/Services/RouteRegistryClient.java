package com.gateway.gatekeeper_gateway.Services;
import com.gateway.gatekeeper_gateway.DTOs.ActiveRouteView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RouteRegistryClient {


    private final RestTemplate restTemplate ;

    @Value("${gatekeeper.admin.url}")
    private String adminUrl ;

    public RouteRegistryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate ;
    }

    public List<ActiveRouteView> fetchRoutes(){

        ActiveRouteView[] routes =
                restTemplate.getForObject(
                        adminUrl+"/gateway/routes",
                        ActiveRouteView[].class
                );

        Arrays.stream(routes).toList().forEach(route ->
                System.out.println(
                        "Endpoint : " + route.routeKey().endpoint() +
                                "  Method : " + route.routeKey().httpMethod() +
                                "   TargetURL :" + route.route().targetUrl()
                ));

        return routes == null
                ? List.of()
                : Arrays.asList(routes);
    }
}
