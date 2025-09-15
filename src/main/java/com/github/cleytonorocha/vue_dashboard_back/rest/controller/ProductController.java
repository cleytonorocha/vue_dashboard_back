package com.github.cleytonorocha.vue_dashboard_back.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cleytonorocha.vue_dashboard_back.helper.ListData;
import com.github.cleytonorocha.vue_dashboard_back.rest.DTO.ProductDTO;
import com.github.cleytonorocha.vue_dashboard_back.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@CrossOrigin(origins = "*")
@Tag(name = "Product", description = "API for managing products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product and returns the created product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        log.info("Creating product: {}", product);
        ProductDTO productDTO = productService.save(product);
        log.info("Product created: {}", productDTO);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Fetches a product by its ID")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);
        ProductDTO productDTO = productService.findById(id);
        log.info("Product fetched: {}", productDTO);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Fetches all products")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@ModelAttribute ListData listData) {
        log.info("Fetching all products with page: {}, lines per page: {}, order by: {}, direction: {}",
                listData.getPage(), listData.getLinesPerPage(), listData.getOrderBy(), listData.getDirection());
        Page<ProductDTO> products = productService.findAll(listData);
        log.info("Products fetched: {}", products);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product by its ID")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        log.info("Updating product with id: {}", id);
        ProductDTO updatedProduct = productService.update(id, product);
        log.info("Product updated: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product with id: {}", id);
        productService.deleteById(id);
        log.info("Product deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
