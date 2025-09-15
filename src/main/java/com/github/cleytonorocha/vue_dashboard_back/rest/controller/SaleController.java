package com.github.cleytonorocha.vue_dashboard_back.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Sale;
import com.github.cleytonorocha.vue_dashboard_back.rest.DTO.SaleDTO;
import com.github.cleytonorocha.vue_dashboard_back.service.SaleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/sales")
@Tag(name = "Sale", description = "API for managing sales")
public class SaleController {

    private final String DEFAULT_LINES_PER_PAGE = "20";
    private final String DEFAULT_PAGE = "0";
    private final String DEFAULT_ORDER = "id";
    private final String DEFAULT_DIRECTION = "ASC";

    private final SaleService salesService;

    @Operation(summary = "Fetch all sales with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of sales fetched successfully with pagination"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<Page<SaleDTO>> findAll(
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_LINES_PER_PAGE) Integer linesPerPage,
            @RequestParam(defaultValue = DEFAULT_ORDER) String orderBy,
            @RequestParam(defaultValue = DEFAULT_DIRECTION) String direction) {

        log.info("Fetching sales with page: {}, linesPerPage: {}, orderBy: {}, direction: {}", page, linesPerPage,
                orderBy, direction);
        Page<SaleDTO> salesPage = salesService.findAll(page, linesPerPage, orderBy, direction);
        log.info("Fetched {} sales", salesPage.getTotalElements());

        return ResponseEntity.ok(salesPage);
    }

    @Operation(summary = "Fetch sale by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> findById(@PathVariable Long id) {
        log.info("Fetching sale with id: {}", id);
        return ResponseEntity.ok(salesService.findById(id));
    }

    @Operation(summary = "Create a new sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sale data")
    })
    @PostMapping
    public ResponseEntity<SaleDTO> save(@RequestBody Sale sales) {
        log.info("Creating new sale: {}", sales);
        SaleDTO savedSales = salesService.save(sales);
        log.info("Sale created with id: {}", savedSales.getId());
        return ResponseEntity.ok(savedSales);
    }

    @Operation(summary = "Update a sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale updated successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@PathVariable Long id, @RequestBody Sale sales) {
        try {
            log.info("Updating sale with id: {}", id);
            SaleDTO updatedSales = salesService.update(id, sales);
            log.info("Sale updated with id: {}", updatedSales.getId());
            return ResponseEntity.ok(updatedSales);
        } catch (RuntimeException e) {
            log.warn("Sale with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sale deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Sale not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Deleting sale with id: {}", id);
        salesService.deleteById(id);
        log.info("Sale with id: {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
