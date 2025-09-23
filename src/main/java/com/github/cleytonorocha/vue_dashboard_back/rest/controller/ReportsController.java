package com.github.cleytonorocha.vue_dashboard_back.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cleytonorocha.vue_dashboard_back.helper.MediaTypes;
import com.github.cleytonorocha.vue_dashboard_back.helper.ReportHelper;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Product;
import com.github.cleytonorocha.vue_dashboard_back.repository.ProductRepository;
import com.github.cleytonorocha.vue_dashboard_back.rest.DTO.MediaTypeDTO;
import com.github.cleytonorocha.vue_dashboard_back.rest.request.ProductRequest;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "reports", produces = {
        MediaTypes.APPLICATION_CSV_VALUE,
        MediaTypes.APPLICATION_PDF_VALUE,
        MediaTypes.APPLICATION_XLSX_VALUE })
public class ReportsController extends ReportHelper {

    private final ProductRepository productRepository;

    @PostMapping("/listProductReport/{type}")
    public ResponseEntity<byte[]> generateProductsReport(
            @PathVariable("type") Integer type,
            @RequestBody ProductRequest filter) throws Exception {

        Map<String, Object> parameters = new HashMap<>();
        ClassPathResource imgFile = new ClassPathResource("static/images/logo.png");

        parameters.put("logo", imgFile.getInputStream());

        List<Product> data = productRepository.findAll();

        byte[] file;
        String filename;
        String contentType;

        MediaTypeDTO mediaTypeDTO = MediaTypeDTO.fromCode(type);

        switch (mediaTypeDTO) {
            case PDF:
                file = exportPdf("products.jrxml", parameters, data);
                filename = "report.pdf";
                contentType = MediaType.parseMediaType(MediaTypes.APPLICATION_PDF_VALUE).toString();
                break;
            case XLSX:
                file = exportExcel("products.jrxml", parameters, data);
                filename = "report.xlsx";
                contentType = MediaType.parseMediaType(MediaTypes.APPLICATION_XLSX_VALUE).toString();
                break;
            case CSV:
                file = exportCsv("products.jrxml", parameters, data);
                filename = "report.csv";
                contentType = MediaType.parseMediaType(MediaTypes.APPLICATION_CSV_VALUE).toString();
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
