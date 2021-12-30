package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    //public ProductDTO getProductByProductId(String product_id);
    List<ProductDTO> getAllProducts();

    public Optional<Product> findByProductId(int product_id);

    public Product addProduct(Product product);

    public void deleteProduct(int product_id);

    public Product updateProduct(int product_id, Product product);
}
