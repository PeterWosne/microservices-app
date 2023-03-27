package ru.ermakow.market.core.converters;

import org.springframework.stereotype.Component;
import ru.ermakow.market.api.ProductDto;
import ru.ermakow.market.core.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setCategoryTitle(p.getCategory().getTitle());
        productDto.setPrice(p.getPrice());
        return productDto;
    }
}
