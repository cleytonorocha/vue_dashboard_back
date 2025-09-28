package com.github.cleytonorocha.vue_dashboard_back.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product_;
import com.github.cleytonorocha.vue_dashboard_back.rest.request.ProductRequest;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

    public static Specification<Product> filterBy(ProductRequest filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (filter.getName() != null && !filter.getName().isEmpty())
                predicate = cb.and(predicate, cb.like(root.get(Product_.NAME), "%" + filter.getName() + "%"));

            if (filter.getProductCategory() != null)
                predicate = cb.and(predicate, cb.equal(root.get(Product_.CATEGORY), filter.getProductCategory()));

            if (filter.getProductStatus() != null)
                predicate = cb.and(predicate, cb.equal(root.get(Product_.STATUS), filter.getProductStatus()));

            if (filter.getMinPrice() != null)
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get(Product_.PRICE), filter.getMinPrice()));

            if (filter.getMaxPrice() != null)
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get(Product_.PRICE), filter.getMaxPrice()));

            return predicate;
        };
    }
}
