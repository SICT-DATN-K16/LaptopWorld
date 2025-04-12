package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.service.report.PdfReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportController {
    private final PdfReportService pdfReportService;

    public ReportController(PdfReportService pdfReportService) {
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<InputStreamResource> downloadOrderReport(@PathVariable long id) {
        try {
            byte[] pdfBytes = this.pdfReportService.createPdfReportBytes("reportOrder", id);

            if (pdfBytes == null) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayInputStream bis = new ByteArrayInputStream(pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=reportOrder-" + id + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
