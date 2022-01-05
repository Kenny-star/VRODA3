package com.kenny.domainclientlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import com.kenny.dtos.Product;
import reactor.core.publisher.Mono;

import java.awt.peer.PanelPeer;

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

    public Mono<Product> getProductByID(final int product_id){
        return webClientBuilder.build().get()
                .uri(hostname + "/products/{product_id}", product_id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    public Flux<Product> getAllProducts() {
        return webClientBuilder.build().get()
                .uri(hostname + "/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }
    public Mono<Product> createProduct(Product product) {
        String url = hostname + "/newProduct/" + product.getProduct_id();
        return webClientBuilder.build()
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class);
    }
    public Mono<Void> deleteProduct(final int product_id){
        return webClientBuilder.build()
                .delete()
                .uri(hostname + "/products/{product_id}", product_id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Product> updateProduct(final int product_id, Product product){
        return webClientBuilder.build()
                .put()
                .uri(hostname + "/products/{product_id}", product_id)
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class);
    }


    public Flux<Product> getProductByTitle(final String title){
        return webClientBuilder.build().get()
                .uri(hostname + "/products/title/{title}", title)
                .retrieve()
                .bodyToFlux(Product.class);
    }


}
