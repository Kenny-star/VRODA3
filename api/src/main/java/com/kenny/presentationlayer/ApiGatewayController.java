package com.kenny.presentationlayer;

import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/gateway")
public class ApiGatewayController {
    private final ProductServiceClient productServiceClient;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "products")
    public Flux<Product> getAllProduct() {
        return productServiceClient.getAllProducts();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "products/{product_id}")
    public Mono<Product> getProductByID(final @PathVariable int product_id){
        return productServiceClient.getProductByID(product_id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(
            value = "newProduct/{product_id}",
            consumes = "application/json",
            produces = "application/json"
    )
    Mono<Product> createProduct(@RequestBody Product product, @PathVariable String product_id) {
        product.setProduct_id(Integer.parseInt(product_id));
        return productServiceClient.createProduct(product);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping (value = "products/{product_id}")
    public Mono<Void> deleteProductById(final @PathVariable int product_id){
        return productServiceClient.deleteProduct(product_id);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(
            value = "products/{product_id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public Mono<Product> updateProduct(@PathVariable final int product_id, @RequestBody Product product){
        return productServiceClient.updateProduct(product_id, product);
    }



}
