package com.example.demo;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

 // Simple Transaction Class (No DB needed)
 class Transaction {
    private final String description;
    private final double amount;
    private final String category;

    public Transaction(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public double getAmount() { return amount; }
    public String getCategory() { return category; }
}

public class FinanceReportGenerator {
   

    public static void main(String[] args) {
        try {
            // Step 1: Create Dummy Transactions
            List<Transaction> transactions = generateDummyTransactions();

            // Step 2: Categorize Spending
            Map<String, Double> categoryTotals = calculateCategorySpending(transactions);

            // Step 3: Generate Chart
            JFreeChart chart = createSpendingChart(categoryTotals);
            byte[] chartImage = generateChartImage(chart);

            // Step 4: Create and Save PDF
            savePdfReport(categoryTotals, chartImage);
            System.out.println("✅ Report Generated: Quarterly_Report.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Dummy transaction data
    private static List<Transaction> generateDummyTransactions() {
        return Arrays.asList(
                new Transaction("Grocery Shopping", 250.75, "Food"),
                new Transaction("Rent Payment", 1200.00, "Housing"),
                new Transaction("Netflix Subscription", 15.99, "Subscription"),
                new Transaction("Spotify Subscription", 9.99, "Subscription"),
                new Transaction("Gym Membership", 45.00, "Subscription"),
                new Transaction("Restaurant Dinner", 85.50, "Food"),
                new Transaction("Online Course", 49.99, "Education"),
                new Transaction("Miscellaneous Shopping", 75.00, "Miscellaneous")
        );
    }

    // Categorize spending
    private static Map<String, Double> calculateCategorySpending(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction tx : transactions) {
            categoryTotals.put(tx.getCategory(), categoryTotals.getOrDefault(tx.getCategory(), 0.0) + tx.getAmount());
        }
        return categoryTotals;
    }

    // Generate Bar Chart
    private static JFreeChart createSpendingChart(Map<String, Double> categoryTotals) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            dataset.addValue(entry.getValue(), "Spending", entry.getKey());
        }
        return ChartFactory.createBarChart(
                "Quarterly Spending by Category",
                "Category",
                "Amount ($)",
                dataset
        );
    }

    // Convert chart to image
    private static byte[] generateChartImage(JFreeChart chart) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(baos, chart, 500, 300);
        return baos.toByteArray();
    }

    // Generate PDF report
    private static void savePdfReport(Map<String, Double> categoryTotals, byte[] chartImage) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Quarterly Financial Report");
        contentStream.endText();

        // Insert chart image
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, chartImage, "chart");
        contentStream.drawImage(pdImage, 50, 400, 500, 300);

        // Add spending table
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 350);
        contentStream.showText("Category-wise Spending:");
        contentStream.endText();

        int yPosition = 330;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
            contentStream.endText();
            yPosition -= 20;
        }

        contentStream.close();
        document.save("Quarterly_Report.pdf");
        document.close();
    }
}

