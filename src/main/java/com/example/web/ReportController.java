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
    public ResponseEntity<byte[]> generateReport(@RequestParam String username) {
        byte[] pdfBytes = generateReportService.generateQuarterlyReport(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("quarterly_report_" + username + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
