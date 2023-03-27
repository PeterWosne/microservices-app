package ru.ermakow.market.core.converters;

import org.springframework.stereotype.Component;
import ru.ermakow.market.api.OrderItemDto;
import ru.ermakow.market.core.entities.OrderItem;


@Component
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem oi) {
        return new OrderItemDto(oi.getProduct().getTitle(), oi.getPricePerProduct(), oi.getQuantity(), oi.getPrice());
    }
}
