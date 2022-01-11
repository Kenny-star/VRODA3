package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductIdLessDTO;
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
<<<<<<< HEAD
import java.util.stream.Collectors;
=======
import java.util.UUID;
>>>>>>> fd690b0... fok this sheit

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
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtos = mapper.entityToModelList(products);

        return dtos;
    }

    @Override
    public ProductDTO findByProductId(String product_id) {
        try{
            Optional<Product> product = productRepository.findById(product_id);
            if(product.get().getTitle() == null)
                throw new NotFoundException("Visit with productId: " + product_id + " does not exist.");
            ProductDTO productDTO = mapper.EntityToModelDTO(product.get());
            return productDTO;
        }

        catch (Exception e){
            throw new NotFoundException("Product of ID " + product_id+" could not be found");
        }
    }


    @Override
    public ProductDTO addProduct(ProductIdLessDTO product) {

        try{
            Product productEntity = mapper.ProductIdLessDtoToEntity(product);
            log.info("Calling product repo to create a product with productCategory: {}", product.getCategory_id());
            Product createdEntity = productRepository.save(productEntity);


            return mapper.EntityToModelDTO(createdEntity);

        }
        catch(DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate productId.", dke);
        }
    }

    @Override
    public void deleteProduct(String product_id){
        productRepository.findById(product_id).ifPresent(p -> productRepository.delete(p));
        LOG.debug("Product of ID: " + product_id + "has been deleted.");
    }

    @Override
    public ProductDTO updateProduct(ProductDTO updatedProduct){
        try{
            Optional<Product> optionalProduct = productRepository.findById(updatedProduct.getProduct_id());
            Product productfound = optionalProduct.get();
            productfound.setPrice(updatedProduct.getPrice());
            productfound.setCategory_id(updatedProduct.getCategory_id());
            productfound.setQuantity(updatedProduct.getQuantity());
            productfound.setDescription(updatedProduct.getDescription());
            productfound.setTitle(updatedProduct.getTitle());
            LOG.debug("product with id {} updated",updatedProduct.getProduct_id());
            Product updatedProductEntity = productRepository.save(productfound);
            return mapper.EntityToModelDTO(updatedProductEntity);
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new NotFoundException("Cannot update product with id: " + updatedProduct.getProduct_id() + ".");
        }
    }

    @Override
    public List<Product> getProductByTitle(String title) {
        //try{
            List<Product> products = productRepository.findProductByTitle(title);
            List<Product> productDTOList = products.stream()
                    .filter(v -> v != null)
                    .map(product -> mapper.entityToModel(product))
                    .collect(Collectors.toList());
            return productDTOList;
        //}
//
//        catch (Exception e){
//            throw new NotFoundException("Product of title " + title+" could not be found");
//        }
    }
}