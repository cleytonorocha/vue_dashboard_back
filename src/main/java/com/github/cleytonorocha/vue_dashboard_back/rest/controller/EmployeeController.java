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

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Employee;
import com.github.cleytonorocha.vue_dashboard_back.service.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
@Tag(name = "Employee", description = "API for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final String DEFAULT_LINES_PER_PAGE = "20";
    private final String DEFAULT_PAGE = "0";
    private final String DEFAULT_ORDER = "id";
    private final String DEFAULT_DIRECTION = "ASC";

    @GetMapping
    public ResponseEntity<Page<Employee>> findAll(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = DEFAULT_LINES_PER_PAGE) Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = DEFAULT_ORDER) String orderBy,
            @RequestParam(value = "direction", defaultValue = DEFAULT_DIRECTION) String direction) {

        Page<Employee> employees = employeeService.findAll(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.update(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
