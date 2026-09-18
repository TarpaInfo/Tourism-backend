package com.tarpa.tourism.financial.controller;

import com.tarpa.tourism.financial.dto.TripExpenseRequestDto;
import com.tarpa.tourism.financial.dto.TripFinancialSummaryDto;
import com.tarpa.tourism.financial.entity.TripExpense;
import com.tarpa.tourism.financial.repository.TripExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/financials")
@RequiredArgsConstructor
public class FinancialController {

    private final TripExpenseRepository expenseRepository;

    @GetMapping("/expenses")
    public ResponseEntity<List<TripExpense>> getAllExpenses() {
        return ResponseEntity.ok(expenseRepository.findAll());
    }

    @PostMapping("/expenses")
    public ResponseEntity<TripExpense> recordExpense(@RequestBody TripExpenseRequestDto dto) {
        TripExpense expense = TripExpense.builder()
                .bookingId(dto.getBookingId())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .currency(dto.getCurrency() != null && !dto.getCurrency().isBlank() ? dto.getCurrency() : "NPR")
                .paidTo(dto.getPaidTo())
                .receiptInvoiceNumber(dto.getReceiptInvoiceNumber())
                .expenseDate(dto.getExpenseDate())
                .notes(dto.getNotes())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(expenseRepository.save(expense));
    }

    @GetMapping("/expenses/booking/{bookingId}")
    public ResponseEntity<List<TripExpense>> getExpensesByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(expenseRepository.findByBookingId(bookingId));
    }

    @GetMapping("/summary/booking/{bookingId}")
    public ResponseEntity<TripFinancialSummaryDto> getBookingFinancialSummary(@PathVariable Long bookingId) {
        List<TripExpense> expenses = expenseRepository.findByBookingId(bookingId);
        BigDecimal total = expenseRepository.calculateTotalExpensesByBookingId(bookingId);

        TripFinancialSummaryDto summary = TripFinancialSummaryDto.builder()
                .bookingId(bookingId)
                .totalExpenses(total)
                .totalExpenseItems(expenses.size())
                .itemizedExpenses(expenses)
                .build();

        return ResponseEntity.ok(summary);
    }
}