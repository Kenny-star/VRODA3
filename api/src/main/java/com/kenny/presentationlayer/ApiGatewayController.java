package com.kenny.presentationlayer;

import com.kenny.domainclientlayer.ProductServiceClient;
import com.kenny.dtos.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

}
