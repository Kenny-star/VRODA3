package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductIdLessDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    //public ProductDTO getProductByProductId(String product_id);
    List<ProductDTO> getAllProducts();

    public ProductDTO getProductById(String product_id);

    public ProductDTO addProduct(ProductIdLessDTO product);

    public void deleteProduct(String product_id);

    List<ProductDTO> getProductByTitle(String title);
    
    public ProductDTO updateProduct(ProductDTO product);
//
//    public ProductDTO addProduct(MultipartFile file, String title,
//                                int categoryId,double price, int quantity,
//                                String description);

}
