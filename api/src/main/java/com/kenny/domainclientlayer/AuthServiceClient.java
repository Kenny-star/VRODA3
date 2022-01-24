package com.kenny.domainclientlayer;


import com.kenny.dtos.Product;
import com.kenny.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class AuthServiceClient {
    private final WebClient.Builder webClientBuilder;
    private String hostname = "http://auth-service";

    @Autowired
    public AuthServiceClient(
            WebClient.Builder webClientBuilder,
            @Value("${app.auth-service.host}") String authServiceHost,
            @Value("${app.auth-service.port}") String authServicePort
    ) {
        this.webClientBuilder = webClientBuilder;
        hostname = "http://" + authServiceHost + ":" + authServicePort;
    }

    public Flux<User> getAllUsers() {
        return webClientBuilder.build().get()
                .uri(hostname + "/users")
                .retrieve()
                .bodyToFlux(User.class);
    }

}
