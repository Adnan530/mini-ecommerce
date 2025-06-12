package com.ecommerce.product_service.specification;

import com.ecommerce.product_service.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filter(String name, String category, Boolean available) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            if (category != null) predicates.add(cb.equal(root.get("category"), category));
            if (available != null) predicates.add(cb.equal(root.get("available"), available));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

