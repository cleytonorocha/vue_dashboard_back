package com.github.cleytonorocha.vue_dashboard_back.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.rest.request.ProductRequest;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

    public static Specification<Product> filterBy(ProductRequest filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter.getName() != null && !filter.getName().isEmpty())
                predicate = cb.and(predicate, cb.like(root.get("name"), "%" + filter.getName() + "%"));

            if (filter.getProductCategory() != null)
                predicate = cb.and(predicate, cb.equal(root.get("category"), filter.getProductCategory()));

            if (filter.getProductStatus() != null)
                predicate = cb.and(predicate, cb.equal(root.get("status"), filter.getProductStatus()));

            if (filter.getMinPrice() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));

            if (filter.getMaxPrice() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));

            return predicate;
        };
    }
}
