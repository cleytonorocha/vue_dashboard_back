package com.github.cleytonorocha.vue_dashboard_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    
}
