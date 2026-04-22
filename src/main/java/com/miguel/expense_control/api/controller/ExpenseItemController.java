package com.miguel.expense_control.api.controller;

import com.miguel.expense_control.api.dto.request.ExpenseItemCreateRequestDTO;
import com.miguel.expense_control.api.dto.request.ExpenseItemUpdateRequestDTO;
import com.miguel.expense_control.api.dto.response.ExpenseItemResponseDTO;
import com.miguel.expense_control.domain.service.ExpenseItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses/{expenseId}/items")
public class ExpenseItemController {

    private final ExpenseItemService expenseItemService;

    public ExpenseItemController(ExpenseItemService expenseItemService) {
        this.expenseItemService = expenseItemService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseItemResponseDTO>> findAll(@PathVariable Long expenseId){
        List<ExpenseItemResponseDTO> items = expenseItemService.findAllByExpenseId(expenseId);
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseItemResponseDTO> findById(@PathVariable Long id){
        ExpenseItemResponseDTO item = expenseItemService.findById(id);
        return ResponseEntity.ok().body(item);
    }

    @PostMapping
    public ResponseEntity<ExpenseItemResponseDTO> saveItem(@PathVariable Long expenseId, @Valid @RequestBody ExpenseItemCreateRequestDTO dto){
        ExpenseItemResponseDTO item = expenseItemService.saveItem(expenseId, dto);
        return ResponseEntity.status(201).body(item);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseItemResponseDTO> updateItem(@PathVariable Long id, @Valid @RequestBody ExpenseItemUpdateRequestDTO dto){
        ExpenseItemResponseDTO item = expenseItemService.updateItem(id, dto);
        return ResponseEntity.ok().body(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        expenseItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
