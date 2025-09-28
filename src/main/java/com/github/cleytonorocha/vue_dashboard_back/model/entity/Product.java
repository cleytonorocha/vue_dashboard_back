package com.github.cleytonorocha.vue_dashboard_back.model.entity;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.springframework.core.io.ClassPathResource;

import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductCategory;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(length = 500, nullable = false)
    private String name;
    
    @Lob
    @Getter
    private String description;

    @Getter
    @Column(nullable = false)
    private Integer stock;

    @Getter
    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal rating;

    @Getter
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Lob
    @Getter
    private String codeQr;

    @Lob
    private String imageUrl;

    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    
    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public BufferedImage getImageUrl() {
        try (InputStream is = new ClassPathResource(imageUrl).getInputStream()) {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
