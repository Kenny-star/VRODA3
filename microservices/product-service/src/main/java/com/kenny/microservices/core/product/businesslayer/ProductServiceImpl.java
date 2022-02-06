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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

import static java.util.UUID.randomUUID;


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
    public ProductDTO getProductById(String product_id) {

            Optional<Product> product = productRepository.findProductByProductId(UUID.fromString(product_id));
            if(product.get().getTitle() == null)
                throw new NotFoundException("Product with productId: " + product_id + " does not exist.");
            ProductDTO productDTO = mapper.EntityToModelDTO(product.get());
            return productDTO;

    }


    @Override
    public ProductDTO addProduct(ProductIdLessDTO product) {

        try{
            Product productEntity = mapper.ProductIdLessDtoToEntity(product);
            log.info("Calling product repo to create a product with productCategory: {}", product.getCategoryId());
            Product createdEntity = productRepository.save(productEntity);


            return mapper.EntityToModelDTO(createdEntity);

        }
        catch(DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate productId.", dke);
        }
    }

    @Override
    public void deleteProduct(String product_id){
        Product product = productRepository.findProductByProductId(UUID.fromString(product_id)).orElse(new Product());
        if(product.getProductId() != null)
            productRepository.delete(product);
        LOG.debug("Product of ID: " + product_id + "has been deleted.");
    }

    @Override
    public ProductDTO updateProduct(ProductDTO updatedProduct){
        Product productEntity = mapper.ProductDTOToEntity(updatedProduct);
        Optional<Product> product = productRepository.findProductByProductId(UUID.fromString(updatedProduct.getProductId()));
        productEntity.setId(product.get().getId());
        log.info("Updating product with productId: {}", product.get().getProductId());
        Product updatedProductEntity = productRepository.save(productEntity);
        return mapper.EntityToModelDTO(updatedProductEntity);
    }

    @Override
    public List<ProductDTO> getProductByTitle(String title) {
        //try{
            List<Product> products = productRepository.findProductsByTitleContaining(title);
            List<ProductDTO> productDTOList = products.stream()
                    .filter(v -> v != null)
                    .map(mapper::EntityToModelDTO)
                    .collect(Collectors.toList());
            return productDTOList;
        //}
//
//        catch (Exception e){
//            throw new NotFoundException("Product of title " + title+" could not be found");
//        }
    }


//    @Override
//    public ProductDTO addProduct(MultipartFile file, String title, int categoryId, double price, int quantity, String description) {
//        try{
//            ProductIdLessDTO product = new ProductIdLessDTO();
////            product.setProductId(randomUUID());
//            product.setCategoryId(categoryId);
//            product.setDescription(description);
//            product.setPrice(price);
//            product.setTitle(title);
//
//            String filename = StringUtils.cleanPath(file.getOriginalFilename());
//
//            if(filename.contains("..")){
//                log.info("Incorrect file format. Try a valid image format");
//            }
//
//            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
//
//            Product productEntity = mapper.ProductIdLessDtoToEntity(product);
//            log.info("Calling product repo to create a product with productCategory: {}", product.getCategoryId());
//            Product createdEntity = productRepository.save(productEntity);
//
//
//            return mapper.EntityToModelDTO(createdEntity);
//
//        }
//        catch(DuplicateKeyException | IOException dke){
//            throw new InvalidInputException("Duplicate productId.", dke);
//        }
//    }
}
