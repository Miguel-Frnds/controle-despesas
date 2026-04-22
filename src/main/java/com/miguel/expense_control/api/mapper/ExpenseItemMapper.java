package com.miguel.expense_control.api.mapper;

import com.miguel.expense_control.api.dto.request.ExpenseItemCreateRequestDTO;
import com.miguel.expense_control.api.dto.response.ExpenseItemResponseDTO;
import com.miguel.expense_control.domain.entity.ExpenseItem;

public class ExpenseItemMapper {

    public static ExpenseItem toEntity(ExpenseItemCreateRequestDTO dto){
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setName(dto.name());
        expenseItem.setUnitPrice(dto.unitPrice());
        expenseItem.setQuantity(dto.quantity());

        return expenseItem;
    }

    public static ExpenseItemResponseDTO toResponseDTO(ExpenseItem item){
        return new ExpenseItemResponseDTO(
                item.getName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getExpense().getId()
        );
    }
}
