package com.kenny.microservices.core.cart.presentationlayer;


import com.kenny.microservices.core.cart.businesslayer.CartService;
import com.kenny.microservices.core.cart.datalayer.CartDTO;
import com.kenny.microservices.core.cart.datalayer.CartIdLessDTO;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
//@Timed("kenny.catalog-composite")
public class CartResource {

    private final CartService cartService;

    public CartResource(CartService cartService){
        this.cartService = cartService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping( "/cart/addToCart")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDTO addToCart(@Valid @RequestBody CartIdLessDTO cart) {
        return cartService.addToCart(cart);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("cart")
    public List<CartDTO> getTheCart() {
        log.info("Getting the cart ");
        return cartService.getTheCart();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/cart/delete/{product_id}")
    public void deleteCart(@PathVariable("product_id") String product_id){
        cartService.deleteCart(product_id);
    }
}
