package com.kenny.microservices.core.product.presentationlayer;

import com.kenny.microservices.core.product.businesslayer.ProductService;
import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductIdLessDTO;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@Timed("kenny.product")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService){
        this.productService = productService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("products")
    public List<ProductDTO> findAllProducts() {
        log.info("Getting products ");
        return productService.getAllProducts();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/products/{product_id}")
    public ProductDTO getProductByID(@PathVariable("product_id") String product_id){
        log.info("Getting product by productid: {}", product_id);
        return productService.getProductById(product_id);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("newProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(
            @Valid @RequestBody ProductIdLessDTO product) {

        return productService.addProduct(product);

    }
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "products/{product_id}")
    public void deleteProduct(@PathVariable("product_id") String product_id){
        productService.deleteProduct(product_id);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/products/{product_id}")
    public ProductDTO updateProduct(@PathVariable("product_id") String product_id, @RequestBody ProductDTO product){
        product.setProductId(product_id);
        return productService.updateProduct(product);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/products/title/{title}")
    public List<Product> getProductByTitle(@PathVariable("title") String title){
        log.info("Getting product by title: {}", title);
        return productService.getProductByTitle(title);
    }
}
