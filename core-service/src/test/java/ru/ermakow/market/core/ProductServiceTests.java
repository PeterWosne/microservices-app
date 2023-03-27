package ru.ermakow.market.core;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ermakow.market.api.ProductDto;
import ru.ermakow.market.core.entities.Category;
import ru.ermakow.market.core.repositories.ProductRepository;
import ru.ermakow.market.core.services.CategoryService;
import ru.ermakow.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void createNewProductTest() {
        //создаем заглушечную категорию
        Category category = new Category();
        category.setId(1l);
        category.setTitle("Food");
        category.setProducts(Collections.emptyList());
        //мокито! верни вот эту категорию в виде optional когда у categoryService будет вызван метод поиска категории по имени
        Mockito.doReturn(Optional.of(category))
                .when(categoryService)
                .getCategoryByTitle("Food");

        ProductDto productDto = new ProductDto(null, "Oranges", BigDecimal.valueOf(200.00), "Food");
        productService.createNewProduct(productDto);

        //проверяем что при вызове createNewProduct срабатывает метод save у productRepository
        Mockito.verify(productRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
