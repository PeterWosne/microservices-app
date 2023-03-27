package ru.ermakov.market.cart.converters;

import org.springframework.stereotype.Component;
import ru.ermakov.market.cart.utils.CartItem;
import ru.ermakow.market.api.CartItemDto;


@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
