package com.example.infrastructure.report;

import com.example.domain.transaction.Transaction;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class PdfReportBuilder {

    public byte[] build(String username, List<Transaction> allTransactions, List<Transaction> subscriptions,
            List<Transaction> recurringIncomes) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText("Transactions Report for: " + username);
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

    public byte[] buildWithChart(String username, Map<LocalDate, BigDecimal> incomeData,
            Map<LocalDate, BigDecimal> expenseData, List<Transaction> transactions) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText("Report for: " + username);
            content.endText();

            JFreeChart chart = createLineChart(incomeData, expenseData);
            byte[] chartBytes = generateChartImage(chart);
            PDImageXObject chartImage = PDImageXObject.createFromByteArray(document, chartBytes, "chart");
            content.drawImage(chartImage, 50, 430, 500, 280);

            BigDecimal totalIncome = incomeData.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalExpense = expenseData.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

            content.setFont(PDType1Font.HELVETICA, 12);
            content.beginText();
            content.newLineAtOffset(50, 400);
            content.showText("Total Income: $" + totalIncome);
            content.endText();

            content.beginText();
            content.newLineAtOffset(50, 380);
            content.showText("Total Expense: $" + totalExpense);
            content.endText();

            content.beginText();
            content.newLineAtOffset(50, 360);
            content.showText("Net: $" + totalIncome.subtract(totalExpense));
            content.endText();

            content.close();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to build PDF with chart", e);
        }
    }

    private JFreeChart createLineChart(Map<LocalDate, BigDecimal> income, Map<LocalDate, BigDecimal> expenses) {
        TimeSeries incomeSeries = new TimeSeries("Income");
        for (Map.Entry<LocalDate, BigDecimal> entry : income.entrySet()) {
            LocalDate date = entry.getKey();
            incomeSeries.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                    entry.getValue().doubleValue());
        }

        TimeSeries expenseSeries = new TimeSeries("Expenses");
        for (Map.Entry<LocalDate, BigDecimal> entry : expenses.entrySet()) {
            LocalDate date = entry.getKey();
            expenseSeries.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                    entry.getValue().doubleValue());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(incomeSeries);
        dataset.addSeries(expenseSeries);

        return ChartFactory.createTimeSeriesChart(
                "Income vs. Expenses by Day",
                "Date",
                "Amount",
                dataset,
                true,
                true,
                false);
    }

    private byte[] generateChartImage(JFreeChart chart) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(out, chart, 500, 300);
        return out.toByteArray();
    }
}