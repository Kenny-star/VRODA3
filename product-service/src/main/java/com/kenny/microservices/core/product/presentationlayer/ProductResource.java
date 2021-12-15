package com.kenny.microservices.core.product.presentationlayer;

import com.kenny.microservices.core.product.businesslayer.ProductService;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Timed("kenny.product")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public List<ProductDTO> findAllProducts() {
        return productService.getAllProducts();
    }
}
