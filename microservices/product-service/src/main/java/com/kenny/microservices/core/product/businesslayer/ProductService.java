package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductIdLessDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    //public ProductDTO getProductByProductId(String product_id);
    List<ProductDTO> getAllProducts();

    public ProductDTO findByProductId(String product_id);

    public ProductDTO addProduct(ProductIdLessDTO product);

    public void deleteProduct(String product_id);

<<<<<<< HEAD
    public Product updateProduct(int product_id, Product product);

    List<Product> getProductByTitle(String title);
=======
    public ProductDTO updateProduct(ProductDTO product);
>>>>>>> fd690b0... fok this sheit
}
