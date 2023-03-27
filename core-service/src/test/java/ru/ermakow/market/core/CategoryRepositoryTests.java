package ru.ermakow.market.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.ermakow.market.core.entities.Category;
import ru.ermakow.market.core.repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;

@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;
    //хибернейтовская сессия, можно через нее заполнять базу данными для тестов

    @Test
    public void findAllCategoriesTest() {
        Category category = new Category();
        category.setTitle("Flowers");
        category.setProducts(Collections.emptyList());
        testEntityManager.persist(category);
        testEntityManager.flush();

        List<Category> categoryList = categoryRepository.findAll();
        Assertions.assertEquals(3, categoryList.size()); // ожидаем что в списке категорий три категории
        Assertions.assertEquals("Flowers", categoryList.get(2).getTitle()); //ожидаем что последняя называется Flowers
    }
}
