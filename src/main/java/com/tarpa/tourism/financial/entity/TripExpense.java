package com.tarpa.tourism.financial.entity;

import com.tarpa.tourism.financial.model.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ExpenseCategory category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(length = 10, nullable = false)
    @Builder.Default
    private String currency = "NPR";

    @Column(nullable = false)
    private String paidTo; // e.g. "Pasang Dawa (Lead Guide)", "Tara Air Lukla Office"

    private String receiptInvoiceNumber;

    private LocalDate expenseDate;

    @Column(length = 500)
    private String notes;

    @Builder.Default
    private LocalDateTime recordedAt = LocalDateTime.now();
}