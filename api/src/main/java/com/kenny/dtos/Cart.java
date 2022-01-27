package com.kenny.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cart {
    private String productId = null;

    private Integer categoryId = null;

    private Integer quantity = null;

    private String title = null;

    private double price  = 0.0;

    private String description = null;
}