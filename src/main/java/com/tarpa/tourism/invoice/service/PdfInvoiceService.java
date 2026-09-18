package com.tarpa.tourism.invoice.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tarpa.tourism.booking.entity.Booking;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class PdfInvoiceService {

    public byte[] generateInvoice(Booking booking) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Set up A4 Document
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Setup Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.BLACK);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);

            // 1. Company Header
            Paragraph title = new Paragraph("TARPA TOURISM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Kageshwori Manohara, Kathmandu, Nepal\nEmail: info@tarpatourism.com | Phone: +977-1-4XXXXXX\n\n", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            // 2. Invoice Details
            document.add(new Paragraph("OFFICIAL INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(11, 61, 145))));
            document.add(new Paragraph("Date Generated: " + LocalDate.now(), normalFont));
            document.add(new Paragraph("Booking Code: " + booking.getBookingCode(), headerFont));
            document.add(new Paragraph("\n"));

            // 3. Client Information
            document.add(new Paragraph("BILL TO:", headerFont));
            document.add(new Paragraph("Name: " + booking.getClient().getFirstName() + " " + booking.getClient().getLastName(), normalFont));
            document.add(new Paragraph("Email: " + booking.getClient().getEmail(), normalFont));
            document.add(new Paragraph("Special Requests: " + (booking.getSpecialRequest() != null ? booking.getSpecialRequest() : "None"), normalFont));
            document.add(new Paragraph("\n"));

            // 4. Invoice Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2, 2}); // Column widths

            // Table Headers
            addTableHeader(table, "Package Details", headerFont);
            addTableHeader(table, "Travelers", headerFont);
            addTableHeader(table, "Travel Date", headerFont);
            addTableHeader(table, "Amount", headerFont);

            // Table Data
            addTableCell(table, booking.getTourPackage().getPackageName(), normalFont);
            addTableCell(table, String.valueOf(booking.getNumberOfTravelers()), normalFont);
            addTableCell(table, booking.getTravelDate().toString(), normalFont);
            addTableCell(table, booking.getCurrency() + " " + booking.getTotalAmount(), normalFont);

            document.add(table);
            document.add(new Paragraph("\n"));

            // 5. Payment Status
            boolean isPaid = "PAID".equalsIgnoreCase(booking.getPaymentStatus());
            Font statusFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, isPaid ? new Color(39, 174, 96) : Color.RED);
            document.add(new Paragraph("PAYMENT STATUS: " + booking.getPaymentStatus().toUpperCase(), statusFont));

            document.add(new Paragraph("\nThank you for exploring the Himalayas with Tarpa Tourism!", subtitleFont));

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF Invoice", e);
        }
    }

    private void addTableHeader(PdfPTable table, String headerTitle, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(headerTitle, font));
        cell.setBackgroundColor(new Color(240, 240, 240));
        cell.setPadding(8);
        table.addCell(cell);
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "", font));
        cell.setPadding(8);
        table.addCell(cell);
    }
}