package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;

import java.util.List;

public interface ProductService {

    //public ProductDTO getProductByProductId(String product_id);
    List<ProductDTO> getAllProducts();

    public Product getProductById(int product_id);

    //public void deleteProductById(int product_id);

    public Product addProduct(Product product);
}
