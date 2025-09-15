package com.github.cleytonorocha.vue_dashboard_back.model.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.cleytonorocha.vue_dashboard_back.exception.EnumIdException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    ELECTRONICS(1, "ELECTRONICS"),
    FOOD(2, "FOOD"),
    CLOTHING(3, "CLOTHING"),
    BOOKS(4, "BOOKS"),
    FURNITURE(5, "FURNITURE"),
    TOYS(6, "TOYS"),
    BEAUTY(7, "BEAUTY"),
    SPORTS(8, "SPORTS"),
    AUTOMOTIVE(9, "AUTOMOTIVE"),
    JEWELRY(10, "JEWELRY"),
    MUSIC(11, "MUSIC"),
    GARDEN(12, "GARDEN"),
    PETS(13, "PETS"),
    OFFICE(14, "OFFICE"),
    HEALTH(15, "HEALTH"),
    BABY(16, "BABY"),
    GROCERY(17, "GROCERY");

    private final Integer cod;
    private final String description;

    public static ProductCategory toEnum(Integer cod) {
        return Arrays.stream(ProductCategory.values())
                .filter(f -> f.getCod().equals(cod))
                .findFirst()
                .orElseThrow(() -> new EnumIdException());
    }

    @JsonValue
    public String toDescription() {
        return description;
    }

    @JsonCreator
    public static ProductCategory from(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("ProductCategory cannot be null");
        }

        return Arrays.stream(ProductCategory.values())
                .filter(cartegory -> (value instanceof Integer && cartegory.getCod().equals(value))
                        || (value instanceof String && cartegory.getDescription().equalsIgnoreCase(value.toString())))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ProductCategory: " + value));
    }
}
