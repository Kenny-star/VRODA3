package com.kenny.microservices.core.product.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, String> {

<<<<<<< HEAD
    List<Product> findProductByTitle(String title);
=======
    Optional<Product> findProductByProductId(UUID product_id);
>>>>>>> eeed32a... Configured the the UUID for CRUDs from second sprint
}
