package com.kenny.domainclientlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthServiceClient {

    private final WebClient.Builder webClientBuilder;
    private String hostname = "http://product-service";

    @Autowired
    public AuthServiceClient(
            WebClient.Builder webClientBuilder,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort
    ) {
        this.webClientBuilder = webClientBuilder;
        hostname = "http://" + productServiceHost + ":" + productServicePort;
    }
}
