package ru.ermakow.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ermakow.market.core.entities.Category;
import ru.ermakow.market.core.repositories.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> getCategoryByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
