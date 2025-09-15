package com.github.cleytonorocha.vue_dashboard_back.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
}
