package com.kenny.microservices.core.product.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    //Optional<Product> findByProductId(int product_id);

}
