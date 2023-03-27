package ru.ermakow.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ermakow.market.api.OrderDto;
import ru.ermakow.market.api.OrderDetailsDto;
import ru.ermakow.market.core.converters.OrderConverter;
import ru.ermakow.market.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;

    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.getAllOrdersForUser(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrderForUser(@RequestHeader String username, @RequestBody(required = false) OrderDetailsDto orderDetailsDto) {

        orderService.createNewOrder(username, orderDetailsDto);
    }
}
