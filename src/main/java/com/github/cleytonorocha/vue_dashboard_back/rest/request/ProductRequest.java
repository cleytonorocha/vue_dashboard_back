package com.github.cleytonorocha.vue_dashboard_back.rest.request;

import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductCategory;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductStatus;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private ProductCategory productCategory;
    private ProductStatus productStatus;
    private Double minPrice;
    private Double maxPrice;
}
