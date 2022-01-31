package com.kenny.microservices.core.cart.datalayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private String productId;
    private int categoryId;
    private String title;
    private double price;
    private int quantity;
    private String description;
    private String image;
}