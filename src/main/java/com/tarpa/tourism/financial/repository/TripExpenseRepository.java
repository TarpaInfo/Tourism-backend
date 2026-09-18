package com.tarpa.tourism.financial.repository;

import com.tarpa.tourism.financial.entity.TripExpense;
import com.tarpa.tourism.financial.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TripExpenseRepository extends JpaRepository<TripExpense, Long> {

    List<TripExpense> findByBookingId(Long bookingId);

    List<TripExpense> findByCategory(ExpenseCategory category);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM TripExpense e WHERE e.bookingId = :bookingId")
    BigDecimal calculateTotalExpensesByBookingId(@Param("bookingId") Long bookingId);
}