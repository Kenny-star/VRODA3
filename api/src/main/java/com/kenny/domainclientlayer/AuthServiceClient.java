package com.kenny.domainclientlayer;


import com.kenny.dtos.RefreshToken;
import com.kenny.dtos.User;
import com.kenny.dtos.UserDetails;
import com.kenny.dtos.UserDetailsAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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

    public Mono<ResponseEntity<String>> signinUser(UserDetails userDetails) {
        return webClientBuilder.build().post()
                .uri(hostname + "/auth/signin")
                .body(Mono.just(userDetails), UserDetails.class)
                .retrieve()
                .toEntity(String.class);
    }

    public Mono<ResponseEntity<String>> signupUser(UserDetailsAuth userDetailsAuth) {
        return webClientBuilder.build().post()
                .uri(hostname + "/auth/signup")
                .body(Mono.just(userDetailsAuth), UserDetailsAuth.class)
                .retrieve()
                .toEntity(String.class);
    }

    public Mono<ResponseEntity<String>> hello(UserDetails userDetails) {
        return webClientBuilder.build().post()
                .uri(hostname + "/hello")
                .body(Mono.just(userDetails), UserDetails.class)
                .retrieve()
                .toEntity(String.class);
    }

    public Mono<ResponseEntity<String>> validateRefreshToken(RefreshToken refreshToken) {
        return webClientBuilder.build().post()
                .uri(hostname + "/auth/refreshToken")
                .body(Mono.just(refreshToken), RefreshToken.class)
                .retrieve()
                .toEntity(String.class);
    }

    public Mono<ResponseEntity<String>> getUserBoard(String token) {

        return webClientBuilder.build().get()
                .uri(hostname + "/role/user")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .retrieve()
                .toEntity(String.class);

    }



    public Mono<ResponseEntity<String>> getClerkBoard(String token) {
        return webClientBuilder.build().get()
                .uri(hostname + "/role/clerk")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .retrieve()
                .toEntity(String.class);
    }
    public Mono<ResponseEntity<String>> getAdminBoard(String token) {
        return webClientBuilder.build().get()
                .uri(hostname + "/role/admin")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .retrieve()
                .toEntity(String.class);
    }

    public Mono<ResponseEntity<String>> getPublicBoard() {
        return webClientBuilder.build().get()
                .uri(hostname + "/public")
                .retrieve()
                .toEntity(String.class);
    }
}
