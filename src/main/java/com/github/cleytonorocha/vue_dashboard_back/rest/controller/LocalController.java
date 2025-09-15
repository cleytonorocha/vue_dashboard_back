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

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Local;
import com.github.cleytonorocha.vue_dashboard_back.service.LocalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/local")
@Tag(name = "Local", description = "API for managing locations")
public class LocalController {

    private final String DEFAULT_LINES_PER_PAGE = "20";
    private final String DEFAULT_PAGE = "0";
    private final String DEFAULT_ORDER = "id";
    private final String DEFAULT_DIRECTION = "ASC";

    private final LocalService localService;

    @GetMapping
    @Operation(summary = "Get all locations", description = "Fetches all registered locations")
    public ResponseEntity<Page<Local>> getAllLocals(
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(0) Integer pagina,
            @RequestParam(defaultValue = DEFAULT_LINES_PER_PAGE) @Min(1) Integer linhasPorPagina,
            @RequestParam(defaultValue = DEFAULT_ORDER) String orderBy,
            @RequestParam(defaultValue = DEFAULT_DIRECTION) String direcao) {
        log.info("Fetching all locations with page: {}, lines per page: {}, order by: {}, direction: {}", pagina, linhasPorPagina, orderBy, direcao);
        Page<Local> locals = localService.findAll(pagina, linhasPorPagina, orderBy, direcao);
        log.info("Locations fetched: {}", locals);
        return ResponseEntity.ok(locals);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get location by ID", description = "Fetches a location by its ID")
    public ResponseEntity<Local> getLofindByIdcalById(@PathVariable Long id) {
        log.info("Fetching location with id: {}", id);
        return ResponseEntity.ofNullable(localService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new location", description = "Creates a new location and returns the created entity")
    public ResponseEntity<Local> save(@RequestBody Local local) {
        log.info("Creating new location: {}", local);
        Local savedLocal = localService.save(local);
        log.info("Location created: {}", savedLocal);
        return ResponseEntity.ok(savedLocal);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a location", description = "Updates an existing location by its ID")
    public ResponseEntity<Local> update(@PathVariable Long id, @RequestBody Local local) {
        log.info("Updating location with id: {}", id);
        try {
            Local updatedLocal = localService.update(id, local);
            log.info("Location updated: {}", updatedLocal);
            return ResponseEntity.ok(updatedLocal);
        } catch (RuntimeException e) {
            log.warn("Failed to update location with id: {}. Reason: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a location", description = "Deletes a location by its ID")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Deleting location with id: {}", id);
        localService.deleteById(id);
        log.info("Location deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
