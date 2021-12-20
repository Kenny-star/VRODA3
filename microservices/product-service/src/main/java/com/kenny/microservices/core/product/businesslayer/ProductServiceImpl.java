package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductRepository;
import com.kenny.microservices.core.product.utils.exceptions.InvalidInputException;
import com.kenny.microservices.core.product.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final  ProductMapper mapper;
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
    public Optional<Product> findByProductId(int product_id) {
        try{
            Optional<Product> product = productRepository.findById(product_id);
            return product;
        }

        catch (Exception e){
            throw new NotFoundException("Product of ID " + product_id+" could not be found");
        }
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

    @Override
    public void deleteProduct(int product_id){
        productRepository.findById(product_id).ifPresent(p -> productRepository.delete(p));
        LOG.debug("Product of ID: " + product_id + "has been deleted.");
    }
}