package com.miguel.expense_control.api.dto.request;

import com.miguel.expense_control.domain.entity.ExpenseCategory;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record ExpenseUpdateRequestDTO(
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        String title,

        @PastOrPresent(message = "A data da despesa não pode ser no futuro")
        LocalDate expenseDate,

        ExpenseCategory category,
        List<ExpenseItemCreateRequestDTO> items,
        Long memberId
) {
}