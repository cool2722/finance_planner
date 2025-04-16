package com.example.infrastructure.report;

import com.example.domain.transaction.Transaction;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class PdfReportBuilder {

    public byte[] build(String username, List<Transaction> allTransactions, List<Transaction> subscriptions, List<Transaction> recurringIncomes) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
    
            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText("Quarterly Report for: " + username);
            content.endText();
    
            content.setFont(PDType1Font.HELVETICA, 12);
            int y = 720;
    
            // Recent Transactions 
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Recent Transactions:");
            content.endText();
    
            for (Transaction tx : allTransactions) {
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(tx.getTimestamp().toLocalDate() + " | " + tx.getReference() + " | " +
                        tx.getMoney().getValue() + " " + tx.getMoney().getCurrency());
                content.endText();
            }
    
            // Recurring Expenses (Subscriptions) 
            y -= 30;
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Active Subscriptions:");
            content.endText();
    
            for (Transaction tx : subscriptions) {
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(tx.getReference() + " | " + tx.getRepeatType().name());
                content.endText();
            }
    
            // Recurring Income (Salary/Investments)
            y -= 30;
            content.beginText();
            content.newLineAtOffset(50, y);
            content.showText("Recurring Incomes:");
            content.endText();
    
            for (Transaction tx : recurringIncomes) {
                y -= 15;
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(tx.getReference() + " | " + tx.getRepeatType().name() + " | " +
                        tx.getMoney().getValue() + " " + tx.getMoney().getCurrency());
                content.endText();
            }
    
            content.close();
    
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to build PDF", e);
        }
    }
}
