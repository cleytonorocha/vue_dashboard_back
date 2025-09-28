package com.github.cleytonorocha.vue_dashboard_back.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.cleytonorocha.vue_dashboard_back.exception.ItemNotFoundException;
import com.github.cleytonorocha.vue_dashboard_back.helper.EnumDTO;
import com.github.cleytonorocha.vue_dashboard_back.helper.ListData;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductCategory;
import com.github.cleytonorocha.vue_dashboard_back.model.enums.ProductStatus;
import com.github.cleytonorocha.vue_dashboard_back.repository.ProductRepository;
import com.github.cleytonorocha.vue_dashboard_back.rest.DTO.ProductDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductDTO> findAll(ListData listData) {

        log.info("Fetching all products with page: {}, linesPerPage: {}, orderBy: {}, direction: {}",
                listData.getPage(),
                listData.getLinesPerPage(), listData.getOrderBy(), listData.getDirection());

        PageRequest pageRequest = PageRequest.of(listData.getPage(), listData.getLinesPerPage(),
                Sort.Direction.valueOf(listData.getDirection()), listData.getOrderBy());
        log.debug("PageRequest created: {}", pageRequest);

        Page<Product> products = productRepository.findAll(pageRequest);
        log.debug("Products fetched: {}", products.getContent());

        Page<ProductDTO> productDTOs = new PageImpl<>(
                products.stream()
                        .map(ProductDTO::toDTO)
                        .toList(),
                products.getPageable(),
                products.getTotalElements());

        log.info("Returning {} products", productDTOs.getTotalElements());

        return productDTOs;

    }

    public ProductDTO findById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .map(ProductDTO::toDTO)
                .orElseThrow(() -> {
                    log.warn("Product with id: {} not found", id);
                    return new ItemNotFoundException();
                });
    }

    public ProductDTO save(ProductDTO product) {
        log.info("Saving product: {}", product);
        Product savedProduct = productRepository.save(ProductDTO.toEntity(product));
        return ProductDTO.toDTO(savedProduct);
    }

    public ProductDTO update(Long id, ProductDTO product) {
        log.info("Updating product with id: {}", id);
        if (productRepository.existsById(id)) {
            product.setId(id);
            Product updatedProduct = productRepository.save(ProductDTO.toEntity(product));
            return ProductDTO.toDTO(updatedProduct);
        } else {
            log.warn("Product with id: {} not found", id);
            throw new ItemNotFoundException();
        }
    }

    public void deleteById(Long id) {
        log.info("Deleting product with id: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            log.warn("Product with id: {} not found", id);
            throw new ItemNotFoundException();
        }
    }

    public List<EnumDTO> combo(Class<? extends Enum<?>> enumClass) {
        if (enumClass == null) {
            throw new IllegalArgumentException("Enum class cannot be null");
        }

        return switch (enumClass.getSimpleName()) {
            case "ProductCategory" -> Arrays.stream(ProductCategory.values())
                    .map(e -> new EnumDTO(e.getCod(), e.getDescription()))
                    .toList();
            case "ProductStatus" -> Arrays.stream(ProductStatus.values())
                    .map(e -> new EnumDTO(e.getCod(), e.getDescription()))
                    .toList();
            default -> throw new IllegalArgumentException("Unsupported enum type: " + enumClass.getSimpleName());
        };
    }

}
