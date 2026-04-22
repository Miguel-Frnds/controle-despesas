package com.miguel.expense_control.api.mapper;

import com.miguel.expense_control.api.dto.request.ExpenseCreateRequestDTO;
import com.miguel.expense_control.api.dto.response.ExpenseResponseDTO;
import com.miguel.expense_control.domain.entity.Expense;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseCreateRequestDTO dto){
        Expense expense = new Expense();
        expense.setTitle(dto.title());
        expense.setItems(dto.items());
        expense.setExpenseDate(dto.expenseDate());
        expense.setCategory(dto.category());

        return expense;
    }

    public static ExpenseResponseDTO toResponseDTO(Expense expense){
        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getTitle(),
                expense.getItems().stream()
                        .map(ExpenseItemMapper::toResponseDTO)
                        .toList(),
                expense.getExpenseDate(),
                expense.getCategory(),
                expense.getTotal(),
                expense.getMember().getName()
        );
    }
}
