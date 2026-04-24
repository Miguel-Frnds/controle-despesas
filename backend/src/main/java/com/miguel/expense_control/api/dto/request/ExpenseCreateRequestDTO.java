package com.miguel.expense_control.api.dto.request;

import com.miguel.expense_control.domain.entity.ExpenseCategory;
import com.miguel.expense_control.domain.entity.ExpenseItem;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ExpenseCreateRequestDTO(
        @NotBlank @Size(max = 100) String title,
        @NotNull List<ExpenseItem> items,
        @NotNull @PastOrPresent LocalDate expenseDate,
        @NotNull ExpenseCategory category,
        @NotNull @Positive Long memberId) {
}
