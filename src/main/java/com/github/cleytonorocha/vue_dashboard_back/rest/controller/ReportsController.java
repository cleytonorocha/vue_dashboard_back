package com.github.cleytonorocha.vue_dashboard_back.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cleytonorocha.vue_dashboard_back.helper.MediaTypes;
import com.github.cleytonorocha.vue_dashboard_back.helper.ReportHelper;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.rest.DTO.MediaTypeDTO;
import com.github.cleytonorocha.vue_dashboard_back.service.ProductService;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;

@RestController
@AllArgsConstructor
@RequestMapping(value = "reports", produces = {
        MediaTypes.APPLICATION_CSV_VALUE,
        MediaTypes.APPLICATION_PDF_VALUE,
        MediaTypes.APPLICATION_XLSX_VALUE })
public class ReportsController extends ReportHelper {

    private final ProductService productService;

    @GetMapping("/listProductReport")
    public ResponseEntity<byte[]> getListProductReport(@RequestParam MediaTypeDTO mediaTypeDTO) throws Exception {
        Map<String, Object> parameters = Map.of("titulo", "Relatório de Produtos");

        List<Product> data = null;

        // Generate report
        byte[] file;
        String filename = "report." + mediaTypeDTO.getExtension();

        switch (mediaTypeDTO) {
            case PDF:
                file = exportPdf("products.jasper", parameters, data);
                break;
            case XLSX:
                file = exportExcel("products.jasper", parameters, data);
                break;
            default:
                throw new IllegalArgumentException("Unsupported media type: " + mediaTypeDTO);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType(MediaTypes.APPLICATION_XLSX_VALUE))
                .body(file);
    }

}
