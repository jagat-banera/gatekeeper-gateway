package com.gateway.gatekeeper_gateway.DTOs;

public class ActiveRouteView {

    private String endpoint;
    private String targetUrl;

    public ActiveRouteView() {
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
