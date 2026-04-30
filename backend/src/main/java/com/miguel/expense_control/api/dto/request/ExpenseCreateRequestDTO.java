package com.miguel.expense_control.api.dto.request;

import com.miguel.expense_control.domain.entity.ExpenseCategory;
import com.miguel.expense_control.domain.entity.ExpenseItem;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ExpenseCreateRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        String title,

        @NotNull (message = "Os itens são obrigatórios")
        List<ExpenseItem> items,

        @NotNull(message = "A data é obrigatória")
        @PastOrPresent(message = "A data da despesa não pode ser no futuro")
        LocalDate expenseDate,

        @NotNull(message = "A categoria é obrigatória")
        ExpenseCategory category,

        @NotNull(message = "O membro é obrigatório")
        @Positive(message = "O membro é inválido")
        Long memberId) {
}
