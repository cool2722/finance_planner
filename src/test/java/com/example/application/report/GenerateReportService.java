package com.example.application.report;

import org.junit.jupiter.api.Test;

import com.example.domain.transaction.TransactionRepository;
import com.example.infrastructure.report.PdfReportBuilder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class GenerateReportServiceTest {
    @Test
    void buildsReportWithoutError() {
        TransactionRepository repo = mock(TransactionRepository.class);
        PdfReportBuilder builder = mock(PdfReportBuilder.class);
        when(builder.build(any(), any(), any(), any())).thenReturn(new byte[]{1, 2, 3});
    
        GenerateReportService service = new GenerateReportService(repo, builder);
        when(repo.findLastNByUserId(any(), anyInt())).thenReturn(List.of());
    
        byte[] report = service.generateQuarterlyReport("user123");
        assertNotNull(report);
        assertEquals(3, report.length);
    }    
}
