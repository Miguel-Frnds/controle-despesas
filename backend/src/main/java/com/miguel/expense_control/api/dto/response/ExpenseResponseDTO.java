package com.miguel.expense_control.api.dto.response;

import com.miguel.expense_control.domain.entity.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ExpenseResponseDTO(
        Long id,
        String title,
        List<ExpenseItemResponseDTO> items,
        LocalDate expenseDate,
        ExpenseCategory category,
        BigDecimal total,
        String memberName
) {
}
