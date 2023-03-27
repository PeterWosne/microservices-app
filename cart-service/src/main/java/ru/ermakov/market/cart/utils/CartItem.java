package ru.ermakov.market.cart.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {

    private Long productId;

    private String productTitle;

    private Integer quantity;

    private BigDecimal pricePerProduct;

    private BigDecimal price;
}
