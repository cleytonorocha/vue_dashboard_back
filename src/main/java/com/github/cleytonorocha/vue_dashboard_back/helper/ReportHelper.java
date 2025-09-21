package com.github.cleytonorocha.vue_dashboard_back.helper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public abstract class ReportHelper {

    private static final String BASE_PATH = "/report/";

    public static JasperPrint fillReport(String reportName,
            Map<String, Object> parametros,
            Collection<?> data) throws JRException {

        String reportPath = BASE_PATH + reportName;
        InputStream jaspInputStream = ReportHelper.class.getResourceAsStream(reportPath);

        if (jaspInputStream == null) {
            throw new IllegalArgumentException("File not found in classpath at: " + reportPath);
        }

        JasperReport jasperReport;

        jasperReport = JasperCompileManager.compileReport(jaspInputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        return JasperFillManager.fillReport(jasperReport, parametros, dataSource);
    }

    public static byte[] exportPdf(String reportName,
            Map<String, Object> parametros,
            Collection<?> data) throws JRException {

        JasperPrint print = fillReport(reportName, parametros, data);
        return JasperExportManager.exportReportToPdf(print);
    }

    public static byte[] exportExcel(String reportName,
            Map<String, Object> parametros,
            Collection<?> data) throws JRException {

        JasperPrint print = fillReport(reportName, parametros, data);

        JRXlsxExporter exporter = new JRXlsxExporter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

        SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
        config.setOnePagePerSheet(false);
        config.setDetectCellType(true);
        config.setCollapseRowSpan(false);

        exporter.setConfiguration(config);
        exporter.exportReport();

        return baos.toByteArray();
    }
}
