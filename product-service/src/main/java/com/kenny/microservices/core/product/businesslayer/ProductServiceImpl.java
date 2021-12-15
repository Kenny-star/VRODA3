package com.kenny.microservices.core.product.businesslayer;

import com.kenny.microservices.core.product.datalayer.Product;
import com.kenny.microservices.core.product.datalayer.ProductDTO;
import com.kenny.microservices.core.product.datalayer.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
}