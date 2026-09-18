package com.tarpa.tourism.util;

import java.math.BigDecimal;

public class EmailTemplateBuilder {

    public static String buildHtmlTemplate(String title, String message) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background-color: #f1f5f9; margin: 0; padding: 20px; }
                        .container { max-width: 600px; margin: 0 auto; background: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.06); }
                        .header { background: #0284c7; color: white; padding: 32px 24px; text-align: center; }
                        .header h1 { margin: 0; font-size: 24px; font-weight: 800; letter-spacing: 1px; }
                        .content { padding: 32px 28px; color: #1e293b; line-height: 1.6; font-size: 14px; }
                        .title { font-size: 18px; font-weight: 700; color: #0f172a; margin-bottom: 16px; border-bottom: 2px solid #f1f5f9; padding-bottom: 10px; }
                        .message { margin-bottom: 24px; white-space: pre-wrap; color: #334155; }
                        .footer { background-color: #0f172a; padding: 24px; text-align: center; color: #94a3b8; font-size: 12px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>TARPA TOURISM</h1>
                            <p style="margin:4px 0 0 0; font-size:12px; opacity:0.9;">Nepal Expedition & Trekking Command</p>
                        </div>
                        <div class="content">
                            <div class="title">%s</div>
                            <div class="message">%s</div>
                        </div>
                        <div class="footer">
                            Tarpa Tourism Pvt. Ltd. | Anamnagar, Kathmandu, Nepal | +977-9801046037
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(title, message);
    }

    public static String buildClientBookingConfirmation(
            String clientName,
            String bookingCode,
            String routeName,
            String travelDate,
            Integer numberOfTravelers,
            BigDecimal totalAmount,
            String currency,
            String pickupLocation,
            String specialInstructions) {

        String formattedAmount = (totalAmount != null) ? totalAmount.toPlainString() : "0.00";
        String currencyCode = (currency == null || currency.isBlank()) ? "USD" : currency;
        int travelers = (numberOfTravelers != null) ? numberOfTravelers : 1;
        String pickup = (pickupLocation == null || pickupLocation.isBlank()) ? "Tribhuvan International Airport (TIA), Kathmandu" : pickupLocation;
        String notes = (specialInstructions == null || specialInstructions.isBlank()) ? "Standard briefing scheduled upon arrival." : specialInstructions;

        boolean hasPercent = (notes != null && notes.contains("%"))
                || (pickup != null && pickup.contains("%"))
                || (clientName != null && clientName.contains("%"))
                || (bookingCode != null && bookingCode.contains("%"));
        // #region agent log
        try {
            String line = String.format(
                    "{\"sessionId\":\"c32bd2\",\"hypothesisId\":\"C\",\"location\":\"EmailTemplateBuilder.java:buildClientBookingConfirmation\",\"message\":\"template inputs\",\"data\":{\"hasPercent\":%s,\"notesLen\":%d},\"timestamp\":%d}%n",
                    hasPercent,
                    notes == null ? 0 : notes.length(),
                    System.currentTimeMillis());
            java.nio.file.Files.writeString(
                    java.nio.file.Path.of("C:\\Users\\NITRO V15\\Desktop\\Tourism Managment System\\tourism-management-system\\debug-c32bd2.log"),
                    line,
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception ignored) {}
        // #endregion

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { margin: 0; padding: 20px; background-color: #f8fafc; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; color: #1e293b; }
                        .main { max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.06); }
                        .header { background: #0284c7; padding: 28px; text-align: center; color: #ffffff; }
                        .header h1 { margin: 0; font-size: 22px; font-weight: 800; }
                        .body { padding: 28px; font-size: 13px; line-height: 1.6; }
                        .card { background: #f1f5f9; border-radius: 12px; padding: 16px; margin: 16px 0; }
                        .card table { width: 100%%; border-collapse: collapse; }
                        .card td { padding: 6px 0; font-size: 13px; }
                        .label { color: #64748b; font-weight: 600; width: 45%%; }
                        .val { font-weight: 700; color: #0f172a; text-align: right; }
                        .footer { background: #0f172a; padding: 20px; text-align: center; color: #94a3b8; font-size: 11px; }
                    </style>
                </head>
                <body>
                    <div class="main">
                        <div class="header">
                            <h1>TARPA TOURISM</h1>
                            <p style="margin: 4px 0 0 0; font-size: 12px; opacity: 0.9;">Expedition Booking Confirmation</p>
                        </div>
                        <div class="body">
                            <p>Namaste <strong>%s</strong>,</p>
                            <p>Your expedition has been confirmed. Below is your official booking record:</p>
                            <div class="card">
                                <table>
                                    <tr><td class="label">Booking Code:</td><td class="val" style="color:#0284c7; font-family: monospace;">%s</td></tr>
                                    <tr><td class="label">Route:</td><td class="val">%s</td></tr>
                                    <tr><td class="label">Travel Date:</td><td class="val">%s</td></tr>
                                    <tr><td class="label">Party Size:</td><td class="val">%d Trekker(s)</td></tr>
                                    <tr><td class="label">Total Invoiced:</td><td class="val" style="color:#059669;">%s %s</td></tr>
                                    <tr><td class="label">Pickup / Transfer:</td><td class="val">%s</td></tr>
                                </table>
                            </div>
                            <p><strong>Remarks:</strong> %s</p>
                        </div>
                        <div class="footer">
                            Tarpa Tourism Pvt. Ltd. | Kathmandu, Nepal | Ops: +977-9801046037
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(
                clientName,
                bookingCode,
                routeName,
                travelDate,
                travelers,
                formattedAmount,
                currencyCode,
                pickup,
                notes
        );
    }

    public static String buildStaffDispatchNotification(
            String bookingCode,
            String clientName,
            String clientEmail,
            String clientPhone,
            String routeName,
            String travelDate,
            Integer numberOfTravelers,
            String vehicleDetails,
            String internalNotes) {

        int travelers = (numberOfTravelers != null) ? numberOfTravelers : 1;

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f1f5f9; margin: 0; padding: 20px; }
                        .box { max-width: 580px; margin: 0 auto; background: #ffffff; border-radius: 12px; border: 1px solid #e2e8f0; overflow: hidden; }
                        .banner { background: #0f172a; color: #38bdf8; padding: 18px; font-weight: 800; font-size: 14px; }
                        .body { padding: 20px; font-size: 13px; color: #334155; line-height: 1.6; }
                        .table { width: 100%%; margin: 12px 0; border-collapse: collapse; }
                        .table td { padding: 6px 0; border-bottom: 1px solid #f1f5f9; }
                        .label { color: #64748b; font-weight: 600; width: 35%%; }
                        .val { font-weight: 700; color: #0f172a; }
                    </style>
                </head>
                <body>
                    <div class="box">
                        <div class="banner">OPERATIONS DISPATCH: %s</div>
                        <div class="body">
                            <p style="margin-top:0;">New confirmed expedition entry ready for permit and guide assignment.</p>
                            <table class="table">
                                <tr><td class="label">Booking Ref:</td><td class="val">%s</td></tr>
                                <tr><td class="label">Lead Client:</td><td class="val">%s</td></tr>
                                <tr><td class="label">Contact:</td><td class="val">%s | %s</td></tr>
                                <tr><td class="label">Route:</td><td class="val">%s</td></tr>
                                <tr><td class="label">Travel Date:</td><td class="val">%s</td></tr>
                                <tr><td class="label">Party Size:</td><td class="val">%d Pax</td></tr>
                                <tr><td class="label">Vehicle:</td><td class="val">%s</td></tr>
                                <tr><td class="label">Notes:</td><td class="val">%s</td></tr>
                            </table>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(
                bookingCode,
                bookingCode,
                clientName,
                clientEmail,
                clientPhone,
                routeName,
                travelDate,
                travelers,
                (vehicleDetails == null || vehicleDetails.isBlank()) ? "Standard Transfer" : vehicleDetails,
                (internalNotes == null || internalNotes.isBlank()) ? "None" : internalNotes
        );
    }
}