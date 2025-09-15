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

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Customer;
import com.github.cleytonorocha.vue_dashboard_back.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@Tag(name = "Customer", description = "API for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    private final String DEFAULT_LINES_PER_PAGE = "20";
    private final String DEFAULT_PAGE = "0";
    private final String DEFAULT_ORDER = "id";
    private final String DEFAULT_DIRECTION = "ASC";

    @Operation(summary = "Retrieve a paginated list of customers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<Page<Customer>> findAll(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = DEFAULT_LINES_PER_PAGE) Integer linesPerPage,
        @RequestParam(value = "orderBy", defaultValue = DEFAULT_ORDER) String orderBy,
        @RequestParam(value = "direction", defaultValue = DEFAULT_DIRECTION) String direction) {

        log.info("Fetching all customers with page: {}, linesPerPage: {}, orderBy: {}, direction: {}", 
                 page, linesPerPage, orderBy, direction);

        Page<Customer> customers = customerService.findAll(page, linesPerPage, orderBy, direction);

        log.info("Returned {} customers", customers.getTotalElements());

        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Retrieve a customer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved customer"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(
        @Parameter(description = "ID of the customer to be fetched") @PathVariable Long id) {

        log.info("Fetching customer with id: {}", id);

        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Customer with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created customer"),
        @ApiResponse(responseCode = "400", description = "Invalid customer data")
    })
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        log.info("Creating new customer: {}", customer);

        Customer savedCustomer = customerService.createCustomer(customer);
        log.info("Created customer with id: {}", savedCustomer.getId());

        return ResponseEntity.ok(savedCustomer);
    }

    @Operation(summary = "Update an existing customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated customer"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(
        @Parameter(description = "ID of the customer to be updated") @PathVariable Long id,
        @RequestBody Customer customer) {

        log.info("Updating customer with id: {}", id);

        try {
            Customer updatedCustomer = customerService.update(id, customer);
            log.info("Updated customer with id: {}", updatedCustomer.getId());
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            log.warn("Customer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a customer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted customer"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Deleting customer with id: {}", id);

        try {
            customerService.deleteById(id);
            log.info("Customer with id {} deleted", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Customer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
