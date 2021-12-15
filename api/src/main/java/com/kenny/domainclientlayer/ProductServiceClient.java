package com.kenny.domainclientlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import com.kenny.dtos.Product;

@Component
public class ProductServiceClient {
    private final WebClient.Builder webClientBuilder;
    private String hostname = "http://product-service";

    @Autowired
    public ProductServiceClient(
            WebClient.Builder webClientBuilder,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") String productServicePort
    ) {
        this.webClientBuilder = webClientBuilder;
        hostname = "http://" + productServiceHost + ":" + productServicePort;
    }

    public Flux<Product> getProductByID(final int product_id){
        return webClientBuilder.build()
                .get()
                .uri(hostname + "/product/{product_id}", product_id)
                .retrieve()
                .bodyToFlux(Product.class);
    }
    public Flux<Product> getAllProducts() {
        return webClientBuilder.build().get()
                .uri(hostname + "/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
