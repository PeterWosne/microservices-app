package ru.ermakov.market.cart.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.ermakov.market.cart.integrations.ProductServiceIntegration;
import ru.ermakov.market.cart.utils.Cart;
import ru.ermakow.market.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;


@Service
@Data
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;

    private final RedisTemplate<String, Object> redisTemplate;

    public Cart getCurrentCart(String cartId) {
        if(!redisTemplate.hasKey(cartId)) {
            Cart cart = new Cart();
            cart.setItems(new ArrayList<>());
            cart.setTotalPrice(BigDecimal.ZERO);
            redisTemplate.opsForValue().set(cartId, cart);
        }

        return (Cart) redisTemplate.opsForValue().get(cartId);
    }

    public void add(String cartId, Long productId) {
        Cart cart = getCurrentCart(cartId);
        ProductDto p = productServiceIntegration.getProductById(productId);
        cart.add(p);
        redisTemplate.opsForValue().set(cartId, cart);
    }

    public void delete(String cartId, Long productId) {
        Cart cart = getCurrentCart(cartId);
        ProductDto p = productServiceIntegration.getProductById(productId);
        cart.delete(p);
        redisTemplate.opsForValue().set(cartId, cart);
    }

    public void clear(String cartId) {
        Cart cart = getCurrentCart(cartId);
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setItems(new ArrayList<>());
        redisTemplate.opsForValue().set(cartId, cart);
    }

    //Мерж корзин
    public void mergeCarts(String cartId, String username) {
        Cart guestCart = getCurrentCart(cartId);
        Cart userCart = getCurrentCart(username);

        guestCart.getItems().forEach(userCart::addCartItem);
        redisTemplate.opsForValue().set(username, userCart);

        //очищаем гостевую корзину
        guestCart.setItems(new ArrayList<>());
        guestCart.setTotalPrice(BigDecimal.ZERO);
        redisTemplate.opsForValue().set(cartId, guestCart);
    }



    // в данном случае корзины хранятся в мапе, у каждой корзины есть уникальный cartId, хранится в оперативной памяти
    // -> не оч хороший подход, не масштабируется
//    private Map<String, Cart> carts;
//
//    @PostConstruct
//    public void init() {
//        carts = new HashMap<>();
//    }

    //запрос корзины
//    public Cart getCurrentCart(String cartId) {
//
//        //ключа cartId нет в мапе -> создаем новую корзину
//        if(!carts.containsKey(cartId)) {
//            Cart cart = new Cart();
//            cart.setTotalPrice(BigDecimal.ZERO);
//            cart.setItems(new ArrayList<>());
//            carts.put(cartId, cart);
//        }
//
//        //возвращаем корзину которая есть в мапе
//        return carts.get(cartId);
//    }

//    public void add(String cartId, Long productId) {
//        ProductDto p = productServiceIntegration.getProductById(productId);
//        this.getCurrentCart(cartId).add(p);
//    }

//    public void delete(String cartId, Long productId) {
//        ProductDto p = productServiceIntegration.getProductById(productId);
//        this.getCurrentCart(cartId).delete(p);
//    }

//    public void clear(String cartId) {
//        this.getCurrentCart(cartId).setItems(new ArrayList<>());
//        this.getCurrentCart(cartId).setTotalPrice(BigDecimal.ZERO);
//    }

//    public void mergeCarts(String cartId, String username) {
//
//        getCurrentCart(cartId).getItems().forEach(s -> {
//            getCurrentCart(username).addCartItem(s);
//        });
//
//        //очищаем гостевую корзину
//        getCurrentCart(cartId).setItems(new ArrayList<>());
//        getCurrentCart(cartId).setTotalPrice(BigDecimal.ZERO);
//    }
}
