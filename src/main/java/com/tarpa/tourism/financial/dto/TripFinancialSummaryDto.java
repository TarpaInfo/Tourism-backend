package com.tarpa.tourism.financial.dto;

import com.tarpa.tourism.financial.entity.TripExpense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripFinancialSummaryDto {
    private Long bookingId;
    private BigDecimal totalExpenses;
    private int totalExpenseItems;
    private List<TripExpense> itemizedExpenses;
}