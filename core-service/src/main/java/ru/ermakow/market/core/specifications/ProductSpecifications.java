package ru.ermakow.market.core.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.ermakow.market.core.entities.Product;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> greaterThanOrEqualTo(BigDecimal price) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get("price"), price));
    }

    public static Specification<Product> lessThanOrEqualTo(BigDecimal price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), String.format("%%%s%%", title));
    }
}
