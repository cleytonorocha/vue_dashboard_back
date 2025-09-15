package com.github.cleytonorocha.vue_dashboard_back.rest.DTO;

import java.time.LocalDateTime;

import com.github.cleytonorocha.vue_dashboard_back.model.entity.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private String orderNumber;
    private String orderDate;
    private Double totalAmount;
    private Long customerId;
    private Long employeeId;
    private Long localId;

    public static SaleDTO toDTO(Sale sale){
        return SaleDTO.builder()
                .id(sale.getId())
                .orderNumber(sale.getOrderNumber())
                .orderDate(sale.getOrderDate().toString())
                .totalAmount(sale.getTotalAmount())
                .customerId(sale.getCustomer().getId())
                .employeeId(sale.getEmployee().getId())
                .localId(sale.getLocal().getId())
                .build();
    }

    public static Sale toEntity(SaleDTO saleDTO){
        return Sale.builder()
                .id(saleDTO.getId())
                .orderNumber(saleDTO.getOrderNumber())
                .orderDate(LocalDateTime.parse(saleDTO.getOrderDate()))
                .totalAmount(saleDTO.getTotalAmount())
                .build();
    }
}
