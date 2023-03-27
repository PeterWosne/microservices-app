package ru.ermakow.market.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemDto {

    private String productTitle;

    private BigDecimal pricePerProduct;

    private Integer quantity;

    private BigDecimal price;

    public OrderItemDto() {}

    public OrderItemDto(String productTitle, BigDecimal pricePerProduct, Integer quantity, BigDecimal price) {
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
