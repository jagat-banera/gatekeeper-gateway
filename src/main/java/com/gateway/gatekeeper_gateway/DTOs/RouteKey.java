package com.gateway.gatekeeper_gateway.DTOs;

import io.netty.handler.codec.http.HttpMethod;

/* Route Key Refers to the Key object of Memory Map of the RouteRegistry ,

endpoint --> the endpoint configured on the frontend by Admin Service Users
httpMethods --- > Refers to COMMON HTTP methods like GET POST DELETE etc

 */

public record RouteKey(
        String endpoint,
        String httpMethod
){

}
