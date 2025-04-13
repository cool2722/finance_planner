package com.example.web;

import com.example.application.report.GenerateReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final GenerateReportService generateReportService;

    public ReportController(GenerateReportService generateReportService) {
        this.generateReportService = generateReportService;
    }

    @GetMapping("/summary")
    public ResponseEntity<byte[]> generateReport(@RequestParam String userId) {
        byte[] pdfBytes = generateReportService.generateQuarterlyReport(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("quarterly_report_" + userId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
