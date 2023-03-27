package ru.ermakow.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ermakow.market.api.CartDto;
import ru.ermakow.market.api.OrderDetailsDto;
import ru.ermakow.market.core.entities.Order;
import ru.ermakow.market.core.entities.OrderItem;
import ru.ermakow.market.core.exceptions.ResourceNotFoundException;
import ru.ermakow.market.core.integrations.CartServiceIntegration;
import ru.ermakow.market.core.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartServiceIntegration cartServiceIntegration;

    private final OrderRepository orderRepository;

    private final ProductService productService;

    @Transactional
    public void createNewOrder(String username, OrderDetailsDto shippingDetailsDto) {
        CartDto cartDto = cartServiceIntegration.getCurrentCartForClient(username);
        Order order = new Order();
        order.setUsername(username);
        order.setPrice(cartDto.getTotalPrice());
        order.setPhone(shippingDetailsDto.getPhone());
        order.setAddress(shippingDetailsDto.getAddress());

        order.setItems(new ArrayList<>());
        cartDto.getItems().forEach(ci -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(productService.getProductById(ci.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
            oi.setPricePerProduct(ci.getPricePerProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getPrice());
            order.getItems().add(oi);
        });

        orderRepository.save(order);

        cartServiceIntegration.clearCart(username);
    }

    public List<Order> getAllOrdersForUser(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
