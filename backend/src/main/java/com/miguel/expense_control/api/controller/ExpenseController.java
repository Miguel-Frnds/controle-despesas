package com.miguel.expense_control.api.controller;

import com.miguel.expense_control.api.dto.request.ExpenseCreateRequestDTO;
import com.miguel.expense_control.api.dto.request.ExpenseUpdateRequestDTO;
import com.miguel.expense_control.api.dto.response.ExpenseResponseDTO;
import com.miguel.expense_control.api.dto.response.MonthComparisonResponseDTO;
import com.miguel.expense_control.domain.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> findAll(){
        List<ExpenseResponseDTO> expenses = expenseService.findAll();
        return ResponseEntity.ok().body(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> findById(@PathVariable Long id){
        ExpenseResponseDTO expense = expenseService.findById(id);
        return ResponseEntity.ok().body(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseCreateRequestDTO dto){
        ExpenseResponseDTO expense = expenseService.createExpense(dto);
        return ResponseEntity.status(201).body(expense);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseUpdateRequestDTO dto){
        ExpenseResponseDTO expense = expenseService.updateExpense(id, dto);
        return ResponseEntity.ok().body(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<BigDecimal> getMonthSummary(@RequestParam Month month, @RequestParam int year){
        BigDecimal total = expenseService.getMonthSummary(month, year);
        return ResponseEntity.ok().body(total);
    }

    @GetMapping("/compare")
    public ResponseEntity<MonthComparisonResponseDTO> compareTwoMonths(@RequestParam Month firstMonth, @RequestParam int firstYear,
                                                                       @RequestParam Month secondMonth, @RequestParam int secondYear){
        MonthComparisonResponseDTO comparison = expenseService.compareTwoMonths(firstMonth, firstYear, secondMonth, secondYear);
        return ResponseEntity.ok().body(comparison);
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<ExpenseResponseDTO>> findAllExpensesByMonthAndYear(@RequestParam Month month, @RequestParam int year){
        List<ExpenseResponseDTO> expenses = expenseService.findAllExpensesByMonthAndYear(month, year);
        return ResponseEntity.ok().body(expenses);
    }
}
