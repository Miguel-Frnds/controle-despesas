package com.miguel.expense_control.domain.service;

import com.miguel.expense_control.api.dto.request.ExpenseItemCreateRequestDTO;
import com.miguel.expense_control.api.dto.request.ExpenseItemUpdateRequestDTO;
import com.miguel.expense_control.api.dto.response.ExpenseItemResponseDTO;
import com.miguel.expense_control.api.mapper.ExpenseItemMapper;
import com.miguel.expense_control.domain.entity.Expense;
import com.miguel.expense_control.domain.entity.ExpenseItem;
import com.miguel.expense_control.domain.exception.ExpenseItemNotFoundException;
import com.miguel.expense_control.domain.repository.ExpenseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseItemService {

    private final ExpenseItemRepository expenseItemRepository;
    private final ExpenseService expenseService;

    public ExpenseItemService(ExpenseItemRepository expenseItemRepository, ExpenseService expenseService) {
        this.expenseItemRepository = expenseItemRepository;
        this.expenseService = expenseService;
    }

    public List<ExpenseItemResponseDTO> findAllByExpenseId(Long expenseId){
        return expenseItemRepository.findAllByExpenseId(expenseId).stream()
                .map(ExpenseItemMapper::toResponseDTO)
                .toList();
    }

    public ExpenseItemResponseDTO findById(Long id){
        return ExpenseItemMapper.toResponseDTO(getById(id));
    }

    public ExpenseItemResponseDTO saveItem(Long expenseId, ExpenseItemCreateRequestDTO dto){
        Expense expense = expenseService.getById(expenseId);
        ExpenseItem item = ExpenseItemMapper.toEntity(dto);
        item.setExpense(expense);
        ExpenseItem saved = expenseItemRepository.save(item);
        expenseService.recalculateTotal(expense);
        return ExpenseItemMapper.toResponseDTO(saved);
    }

    public ExpenseItemResponseDTO updateItem(Long id, ExpenseItemUpdateRequestDTO dto){
        ExpenseItem foundItem = getById(id);

        if(dto.name() != null && !dto.name().isBlank()){
            foundItem.setName(dto.name());
        }

        if(dto.quantity() != null){
            foundItem.setQuantity(dto.quantity());
        }

        if(dto.unitPrice() != null){
            foundItem.setUnitPrice(dto.unitPrice());
        }

        expenseItemRepository.save(foundItem);
        expenseService.recalculateTotal(foundItem.getExpense());

        return ExpenseItemMapper.toResponseDTO(foundItem);
    }

    public void deleteItem(Long id){
        ExpenseItem foundItem = getById(id);
        Expense expense = foundItem.getExpense();
        expenseItemRepository.delete(foundItem);
        expenseService.recalculateTotal(expense);
    }

    private ExpenseItem getById(Long id){
        return expenseItemRepository.findById(id)
                .orElseThrow(ExpenseItemNotFoundException::new);
    }
}
