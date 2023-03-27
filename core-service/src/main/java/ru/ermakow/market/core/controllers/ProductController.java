package ru.ermakow.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ermakow.market.api.ProductDto;
import ru.ermakow.market.core.converters.ProductConverter;
import ru.ermakow.market.core.entities.Product;
import ru.ermakow.market.core.exceptions.AppError;
import ru.ermakow.market.core.exceptions.ResourceNotFoundException;
import ru.ermakow.market.core.services.ProductService;
import ru.ermakow.market.core.specifications.ProductSpecifications;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "products", description = "Методы для работы с продуктами") //аннотация для контроллера
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min_price", required = false) Integer minPrice,
                                           @RequestParam(name = "max_price", required = false) Integer maxPrice,
                                           @RequestParam(name = "title_part", required = false) String titlePart) {

        if(page < 0) page = 1;

        Specification<Product> spec = Specification.where(null);

        if(minPrice != null) {
            spec = spec.and(ProductSpecifications.greaterThanOrEqualTo(BigDecimal.valueOf(minPrice)));
        }

        if(maxPrice != null) {
            spec = spec.and(ProductSpecifications.lessThanOrEqualTo(BigDecimal.valueOf(maxPrice)));
        }

        if(titlePart != null) {

            spec = spec.and(ProductSpecifications.titleLike(titlePart.toLowerCase()));
        }

        return productService.getAllProducts(page - 1, 8, spec)
                .map(productConverter::entityToDto);
    }
    // отдавать все поля из БД одним методом неправильно(например если их много 500k -
    // представим что в гейтвее прописано что на запрос есть максимум 20 сек, тогда гейтвей зарубит запрос на выдачу 500к полей по таймауту)
    // -> будем использовать пагинацию с помощью Spring Data JPA

    //postman -> http://localhost:8189/market-core/api/v1/products?p=1


    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") @Parameter(description = "Id продукта", required = true) Long id) {
        return productConverter.entityToDto(productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found")));
    }

    @Operation(
            summary = "Запрос на создание продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт создан", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductDto productDto) {
        productService.createNewProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }
}


