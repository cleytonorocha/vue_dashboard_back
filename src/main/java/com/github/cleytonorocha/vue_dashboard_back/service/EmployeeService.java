package com.github.cleytonorocha.vue_dashboard_back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.cleytonorocha.vue_dashboard_back.exception.ItemNotFoundException;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Employee;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.EmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        log.info("Fetching all employees with page: {}, linesPerPage: {}, orderBy: {}, direction: {}", page, linesPerPage, orderBy, direction);

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        log.debug("PageRequest created: {}", pageRequest);

        Page<Employee> employees = employeeRepository.findAll(pageRequest);
        log.debug("Employees fetched: {}", employees.getContent());

        log.info("Returning {} employees", employees.getTotalElements());

        return employees;
    }

    public Employee findById(Long id) {
        log.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee with id: {} not found", id);
                    return new ItemNotFoundException();
                });
    }

    public Employee save(Employee employee) {
        log.info("Creating employee: {}", employee);
        return employeeRepository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        log.info("Updating employee with id: {}", id);
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            return employeeRepository.save(employee);
        } else {
            log.warn("Employee with id: {} not found", id);
            throw new ItemNotFoundException();
        }
    }

    public void deleteById(Long id) {
        log.info("Deleting employee with id: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            log.warn("Employee with id: {} not found", id);
            throw new ItemNotFoundException();
        }
    }
}
