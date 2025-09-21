package com.github.cleytonorocha.vue_dashboard_back.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cleytonorocha.vue_dashboard_back.helper.MediaTypes;
import com.github.cleytonorocha.vue_dashboard_back.helper.ReportHelper;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.repository.ProductRepository;
import com.github.cleytonorocha.vue_dashboard_back.repository.specification.ProductSpecification;
import com.github.cleytonorocha.vue_dashboard_back.rest.DTO.MediaTypeDTO;
import com.github.cleytonorocha.vue_dashboard_back.rest.request.ProductRequest;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "reports", produces = {
        MediaTypes.APPLICATION_CSV_VALUE,
        MediaTypes.APPLICATION_PDF_VALUE,
        MediaTypes.APPLICATION_XLSX_VALUE })
public class ReportsController extends ReportHelper {

    private final ProductRepository productRepository;

    @GetMapping("/listProductReport")
    public ResponseEntity<byte[]> getListProductReport(@RequestParam MediaTypeDTO mediaTypeDTO, ProductRequest filter)
            throws Exception {
        Map<String, Object> parameters = Map.of("titulo", "Relatório de Produtos");

        List<Product> data = productRepository.findAll(ProductSpecification.filterBy(filter));

        byte[] file;
        String filename;
        String contentType;

        switch (mediaTypeDTO) {
            case PDF:
                file = exportPdf("products.jasper", parameters, data);
                filename = "report.pdf";
                contentType = MediaTypes.APPLICATION_PDF_VALUE;
                break;
            case XLSX:
                file = exportExcel("products.jasper", parameters, data);
                filename = "report.xlsx";
                contentType = MediaTypes.APPLICATION_XLSX_VALUE;
                break;
            case CSV:
                file = exportCsv("products.jasper", parameters, data);
                filename = "report.csv";
                contentType = MediaTypes.APPLICATION_CSV_VALUE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported media type: " + mediaTypeDTO);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }
}
