package com.miguel.expense_control.domain.repository;

import com.miguel.expense_control.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.total) FROM Expense e WHERE MONTH(e.expenseDate) = :month and YEAR(e.expenseDate) = :year")
    BigDecimal sumByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(e) FROM Expense e WHERE MONTH(e.expenseDate) = :month and YEAR(e.expenseDate) = :year")
    int countByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT e FROM Expense e Where e.expenseDate BETWEEN :start and :end")
    List<Expense> findAllByMonthAndYear(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
