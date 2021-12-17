package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductRepository;
import com.kenny.microservices.core.product.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repo, ProductMapper mapper) {
        this.productRepository = repo;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> bills = productRepository.findAll();
        List<ProductDTO> dtos = mapper.entityToModelList(bills);

        return dtos;
    }

    @Override
    public Product getProductById(int product_id) {
        return productRepository.getOne(product_id);
    }
/*
    @Override
    public void deleteProductById(int product_id) {
        log.debug("Product object is deleted with this product_id: " + product_id);
        Product product = productRepository.findByProductId(product_id).orElse(new Product());
        if(product.getTitle() !=null)
            productRepository.delete(product);

        log.debug("Product deleted");
    }
*/
    @Override
    public Product addProduct(Product product) {

        try{
            Product productEntity = mapper.ProductToEntity(product);
            log.info("Calling product repo to create a product with productId: {}", product.getProduct_id());
            Product createdEntity = productRepository.save(productEntity);
            return mapper.entityToModel(createdEntity);
        }
        catch(DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate productId.", dke);
        }
    }
}