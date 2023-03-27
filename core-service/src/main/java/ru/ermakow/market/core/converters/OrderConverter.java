package ru.ermakow.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ermakow.market.api.OrderDto;
import ru.ermakow.market.core.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final OrderItemConverter converter;

    public OrderDto entityToDto(Order o) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(o.getId());
        orderDto.setItems(o.getItems().stream().map(converter::entityToDto).collect(Collectors.toList()));
        orderDto.setPrice(o.getPrice());
        orderDto.setPhone(o.getPhone());
        orderDto.setAddress(o.getAddress());
        orderDto.setCreatedAt(o.getCreatedAt());
        return orderDto;
    }
}
