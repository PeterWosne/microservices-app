package ru.ermakov.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ermakov.market.cart.converters.CartConverter;
import ru.ermakov.market.cart.services.CartService;
import ru.ermakow.market.api.CartDto;
import ru.ermakow.market.api.StringResponse;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CartConverter cartConverter;

    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{cartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String cartId) {
        String currentCartId = selectCartId(username, cartId);

        //если пришел хедер username -> значит мы авторизовались
        if(username != null && cartService.getCurrentCart(cartId).getItems().size() != 0) {
            cartService.mergeCarts(cartId, username);
        }

        return cartConverter.entityToDto(cartService.getCurrentCart(currentCartId));
    }

    @GetMapping("{cartId}/add/{productId}")
    public void addToCart(@RequestHeader(required = false) String username, @PathVariable String cartId, @PathVariable Long productId) {
        String currentCartId = selectCartId(username, cartId);
        cartService.add(currentCartId, productId);
    }

    @GetMapping("{cartId}/delete/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String cartId,@PathVariable Long productId) {
        String currentCartId = selectCartId(username, cartId);
        cartService.delete(currentCartId, productId);
    }

    @GetMapping("{cartId}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String cartId) {
        String currentCartId = selectCartId(username, cartId);
        cartService.clear(currentCartId);
    }

    //служебный метод, определяет какую корзину будем показывать - гостя или вошедшего юзера
    private String selectCartId(String username, String cartId) {
        if(username != null) {
            return username;
        }
        return cartId;
    }
}
