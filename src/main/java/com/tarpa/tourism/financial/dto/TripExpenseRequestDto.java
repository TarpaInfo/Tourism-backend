package com.tarpa.tourism.financial.dto;

import com.tarpa.tourism.financial.model.ExpenseCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TripExpenseRequestDto {
    private Long bookingId;
    private ExpenseCategory category;
    private BigDecimal amount;
    private String currency;
    private String paidTo;
    private String receiptInvoiceNumber;
    private LocalDate expenseDate;
    private String notes;
}