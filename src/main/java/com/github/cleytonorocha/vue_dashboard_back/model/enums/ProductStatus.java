package com.github.cleytonorocha.vue_dashboard_back.model.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.cleytonorocha.vue_dashboard_back.exception.EnumIdException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    AVAILABLE(1, "AVAILABLE"),
    OUT_OF_STOCK(2, "OUT OF STOCK"),
    DISCONTINUED(3, "DISCONTINUED"),
    PENDING(4, "PENDING"),
    RESERVED(5, "RESERVED");

    private final Integer cod;
    private final String description;

    public static ProductStatus toEnum(Integer cod) {
        return Arrays.stream(ProductStatus.values())
                .filter(f -> f.getCod().equals(cod))
                .findFirst()
                .orElseThrow(() -> new EnumIdException());
    }

    @JsonValue
    public String toDescription() {
        return description;
    }

    @JsonCreator
    public static ProductStatus from(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("ProductCategory cannot be null");
        }

        return Arrays.stream(ProductStatus.values())
                .filter(status -> (value instanceof Integer && status.getCod().equals(value))
                        || (value instanceof String && status.getDescription().equalsIgnoreCase(value.toString())))
                .findFirst()
                .orElse(null);
    }
}
