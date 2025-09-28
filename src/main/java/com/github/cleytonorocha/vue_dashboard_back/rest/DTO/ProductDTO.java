package com.github.cleytonorocha.vue_dashboard_back.rest.DTO;

import java.math.BigDecimal;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductCategory;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 500, message = "Name should not exceed 500 characters")
    private String name;

    @Size(max = 2000, message = "Description should not exceed 2000 characters")
    private String description;

    @NotNull(message = "Stock is mandatory")
    @Min(value = 0, message = "Stock should not be less than 0")
    private Integer stock;

    @Min(value = 0, message = "Rating should not be less than 0")
    private BigDecimal rating;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    private BigDecimal price;

    private String codeQr;

    private String imageUrl;

    @NotNull(message = "Category is mandatory")
    private ProductCategory category;

    @NotNull(message = "Status is mandatory")
    private ProductStatus status;

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .stock(productDTO.getStock())
                .rating(productDTO.getRating())
                .price(productDTO.getPrice())
                .codeQr(productDTO.getCodeQr())
                .imageUrl(productDTO.getImageUrl())
                .category(productDTO.getCategory())
                .status(productDTO.getStatus())
                .build();
    }

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .rating(product.getRating())
                .price(product.getPrice())
                .codeQr(product.getCodeQr())
                .imageUrl(product.getImageUrl().toString())
                .category(product.getCategory())
                .status(product.getStatus())
                .build();
    }
}
