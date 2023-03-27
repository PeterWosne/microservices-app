package ru.ermakow.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ermakow.market.api.ProductDto;
import ru.ermakow.market.core.entities.Product;
import ru.ermakow.market.core.exceptions.ResourceNotFoundException;
import ru.ermakow.market.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> getAllProducts(Integer page, Integer pageSize, Specification<Product> specification) {
        return productRepository.findAll(specification, PageRequest.of(page, pageSize));
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        Product p = new Product();
        p.setTitle(productDto.getTitle());
        p.setCategory(categoryService.getCategoryByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category " + productDto.getCategoryTitle() + " not found")));
        p.setPrice(productDto.getPrice());
        productRepository.save(p);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
