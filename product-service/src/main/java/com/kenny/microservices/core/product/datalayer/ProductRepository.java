package com.kenny.microservices.core.product.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    //List<Product> getAllProduct();
}
