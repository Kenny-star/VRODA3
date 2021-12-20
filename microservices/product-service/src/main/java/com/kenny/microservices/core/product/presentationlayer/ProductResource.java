package com.kenny.microservices.core.product.presentationlayer;

import com.kenny.microservices.core.product.businesslayer.ProductService;
import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Timed("kenny.product")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("products")
    public List<ProductDTO> findAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{product_id}")
    public Optional<Product> getProductByID(@PathVariable("product_id") int product_id){
        log.info("Getting product by productid: {}", product_id);
        return productService.findByProductId(product_id);
    }
/*
    @DeleteMapping("delProduct/{product_id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteProductById(@PathVariable("product_id") int product_id) {
        log.info("Deleting products with productId: {}", product_id );
        productService.deleteProductById(product_id);
    }
*/
    @PostMapping("newProduct/{product_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(
            @Valid @RequestBody Product product,
            @PathVariable("product_id") int product_id) {

        product.setProduct_id(product_id);
        log.debug("Calling ProductService:addProduct with productId: {}", product_id);
        return productService.addProduct(product);

    }

    @DeleteMapping(value = "products/{product_id}")
    public void deleteProduct(@PathVariable("product_id") int product_id){
        productService.deleteProduct(product_id);
    }
}