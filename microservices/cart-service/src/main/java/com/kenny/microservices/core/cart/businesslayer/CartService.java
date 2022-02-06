package com.kenny.microservices.core.cart.businesslayer;

import com.kenny.microservices.core.cart.datalayer.CartDTO;
import com.kenny.microservices.core.cart.datalayer.CartIdLessDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CartService {
    public CartDTO addToCart(CartIdLessDTO product);

    List<CartDTO> getTheCart();

    public void deleteCart(String product_id);

//    public CartDTO addToCart(MultipartFile file, String title,
//                                 int categoryId, double price, int quantity,
//                                 String description);
}
