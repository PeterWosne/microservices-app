package ru.ermakov.market.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ermakow.market.api.ProductDto;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    private final WebClient productServiceWebClient;

    public ProductDto getProductById(Long id) {
        return productServiceWebClient
                .get()
                .uri("api/v1/products/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
    // toBodilessEntity(), Flux()

//    private final RestTemplate restTemplate;
//    public ProductDto getProductById(Long id) {
//        return restTemplate.getForObject("http://localhost:8189/market-core/api/v1/products/" + id, ProductDto.class);
//    }
}
