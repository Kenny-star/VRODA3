package com.kenny.microservices.core.cart.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findProductByProductId(UUID product_id);
}
