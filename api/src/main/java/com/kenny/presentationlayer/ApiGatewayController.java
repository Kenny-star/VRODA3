package com.kenny.presentationlayer;

import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/gateway")
public class ApiGatewayController {
    private final ProductServiceClient productServiceClient;

    @GetMapping(value = "products")
    public Flux<Product> getAllProduct() {
        return productServiceClient.getAllProducts();
    }

    @GetMapping(value = "products/{product_id}")
    public Mono<Product> getProductByID(final @PathVariable int product_id){
        return productServiceClient.getProductByID(product_id);
    }

    @PostMapping(
            value = "newProduct/{product_id}",
            consumes = "application/json",
            produces = "application/json"
    )
    Mono<Product> createProduct(@RequestBody Product product, @PathVariable String product_id) {
        product.setProduct_id(Integer.parseInt(product_id));
        return productServiceClient.createProduct(product);
    }

    @DeleteMapping (value = "products/{product_id}")
    public Mono<Void> deleteProductById(final @PathVariable int product_id){
        return productServiceClient.deleteProduct(product_id);
    }

}
