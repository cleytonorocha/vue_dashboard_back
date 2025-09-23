package com.github.cleytonorocha.vue_dashboard_back.helper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public abstract class ReportHelper {

    private static final String BASE_PATH = "/report/";

    public static JasperPrint fillReport(String reportName,
            Map<String, Object> parameters,
            Collection<?> data) throws JRException {

        String reportPath = BASE_PATH + reportName;
        InputStream jaspInputStream = ReportHelper.class.getResourceAsStream(reportPath);

        if (jaspInputStream == null) {
            throw new IllegalArgumentException("File not found in classpath at: " + reportPath);
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jaspInputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    public static byte[] exportPdf(String reportName,
            Map<String, Object> parameters,
            Collection<?> data) throws JRException {

        JasperPrint print = fillReport(reportName, parameters, data);
        return JasperExportManager.exportReportToPdf(print);
    }

    public static byte[] exportExcel(String reportName,
            Map<String, Object> parameters,
            Collection<?> data) throws JRException {

        JasperPrint print = fillReport(reportName, parameters, data);

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

    public static byte[] exportCsv(String reportName,
            Map<String, Object> parameters,
            Collection<?> data) throws JRException {

        JasperPrint print = fillReport(reportName, parameters, data);

        JRCsvExporter exporter = new JRCsvExporter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Configura entrada e saída
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(baos));

        // Configurações CSV (opcional)
        SimpleCsvExporterConfiguration config = new SimpleCsvExporterConfiguration();
        config.setFieldDelimiter(";"); // delimitador de campos
        config.setRecordDelimiter("\n"); // delimitador de linhas
        exporter.setConfiguration(config);

        // Executa exportação
        exporter.exportReport();

        return baos.toByteArray();
    }
}
