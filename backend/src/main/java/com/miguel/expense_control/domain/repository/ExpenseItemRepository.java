package com.miguel.expense_control.domain.repository;

import com.miguel.expense_control.domain.entity.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Long> {
    List<ExpenseItem> findAllByExpenseId(Long expenseId);
}
