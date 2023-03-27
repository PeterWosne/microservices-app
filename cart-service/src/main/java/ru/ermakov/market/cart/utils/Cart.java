package ru.ermakov.market.cart.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ermakow.market.api.ProductDto;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class Cart {

    private List<CartItem> items;

    private BigDecimal totalPrice;

    public void add(ProductDto p) {
        for(CartItem item : items) {
            if(item.getProductId().equals(p.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                item.setPrice(item.getPrice().add(item.getPricePerProduct()));
                recalculate();
                return;
            }
        }

        CartItem item = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        items.add(item);
        recalculate();
    }

    public void addCartItem(CartItem guestCartItem) {
        for(CartItem item : items) {
            if(item.getProductId().equals(guestCartItem.getProductId())) {
                item.setQuantity(item.getQuantity() + guestCartItem.getQuantity());
                recalculate();
                return;
            }
        }

        items.add(guestCartItem);
        recalculate();
    }

    public void delete(ProductDto p) {
        for(CartItem item : items) {
            if(item.getProductId().equals(p.getId())) {
                if(item.getQuantity() == 1) {
                    items.remove(item);
                    recalculate();
                    return;
                }
                item.setQuantity(item.getQuantity() - 1);
                item.setPrice(item.getPrice().subtract(item.getPricePerProduct()));
                recalculate();
            }
        }
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for(CartItem item : items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }
}
