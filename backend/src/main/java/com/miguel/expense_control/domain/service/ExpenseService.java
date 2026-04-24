package com.miguel.expense_control.domain.service;

import com.miguel.expense_control.api.dto.request.ExpenseCreateRequestDTO;
import com.miguel.expense_control.api.dto.request.ExpenseUpdateRequestDTO;
import com.miguel.expense_control.api.dto.response.ExpenseResponseDTO;
import com.miguel.expense_control.api.dto.response.MonthComparisonResponseDTO;
import com.miguel.expense_control.api.mapper.ExpenseItemMapper;
import com.miguel.expense_control.api.mapper.ExpenseMapper;
import com.miguel.expense_control.domain.entity.Expense;
import com.miguel.expense_control.domain.entity.ExpenseItem;
import com.miguel.expense_control.domain.entity.Member;
import com.miguel.expense_control.domain.exception.ExpenseNotFound;
import com.miguel.expense_control.domain.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberService memberService;

    public ExpenseService(ExpenseRepository expenseRepository, MemberService memberService) {
        this.expenseRepository = expenseRepository;
        this.memberService = memberService;
    }

    public List<ExpenseResponseDTO> findAll(){
        return expenseRepository.findAll().stream()
                .map(ExpenseMapper::toResponseDTO)
                .toList();
    }

    public ExpenseResponseDTO findById(Long id){
        return ExpenseMapper.toResponseDTO(getById(id));
    }

    public ExpenseResponseDTO createExpense(ExpenseCreateRequestDTO dto){
        Expense expense = ExpenseMapper.toEntity(dto);

        Member member = memberService.getById(dto.memberId());
        expense.setMember(member);

        for (ExpenseItem item : expense.getItems()) {
            item.setExpense(expense);
        }
        
        expense.setTotal(calculateTotal(expense));
        return ExpenseMapper.toResponseDTO(expenseRepository.save(expense));
    }

    public ExpenseResponseDTO updateExpense(Long id, ExpenseUpdateRequestDTO dto){
        Expense foundExpense = getById(id);

        if(dto.title() != null && !dto.title().isBlank()) {
            foundExpense.setTitle(dto.title());
        }

        if(dto.memberId() != null) {
            Member member = memberService.getById(dto.memberId());
            foundExpense.setMember(member);
        }

        if(dto.category() != null) {
            foundExpense.setCategory(dto.category());
        }

        if(dto.expenseDate() != null) {
            foundExpense.setExpenseDate(dto.expenseDate());
        }

        if(dto.items() != null && !dto.items().isEmpty()) {
            List<ExpenseItem> items = dto.items().stream()
                    .map(ExpenseItemMapper::toEntity)
                    .toList();
            items.forEach(item -> item.setExpense(foundExpense));
            foundExpense.getItems().clear();
            foundExpense.getItems().addAll(items);
        }

        foundExpense.setTotal(calculateTotal(foundExpense));

        return ExpenseMapper.toResponseDTO(expenseRepository.save(foundExpense));
    }

    public void delete(Long id){
        Expense foundExpense = getById(id);
        expenseRepository.delete(foundExpense);
    }

    public BigDecimal getMonthSummary(Month month, int year){
        BigDecimal total = expenseRepository.sumByMonthAndYear(month.getValue(), year);
        if(total == null) {
            return BigDecimal.ZERO;
        }
        return total;
    }

    public MonthComparisonResponseDTO compareTwoMonths(Month firstMonth, int firstYear, Month secondMonth, int secondYear){
        int firstMonthExpenseCount = expenseRepository.countByMonthAndYear(firstMonth.getValue(), firstYear);
        int secondMonthExpenseCount = expenseRepository.countByMonthAndYear(secondMonth.getValue(), secondYear);
        BigDecimal firstMonthTotal = expenseRepository.sumByMonthAndYear(firstMonth.getValue(), firstYear);
        BigDecimal secondMonthTotal = expenseRepository.sumByMonthAndYear(secondMonth.getValue(), secondYear);


        if(firstMonthTotal == null) firstMonthTotal = BigDecimal.ZERO;
        if(secondMonthTotal == null) secondMonthTotal = BigDecimal.ZERO;

        BigDecimal difference = secondMonthTotal.subtract(firstMonthTotal);
        Month highestSpendingMonth = firstMonthTotal.compareTo(secondMonthTotal) >= 0 ? firstMonth : secondMonth;

        return new MonthComparisonResponseDTO(
                firstMonth.name(),
                firstMonthTotal,
                firstMonthExpenseCount,
                secondMonth.name(),
                secondMonthTotal,
                secondMonthExpenseCount,
                difference,
                highestSpendingMonth.name()

        );
    }

    public List<ExpenseResponseDTO> findAllExpensesByMonthAndYear(Month month, int year){
        LocalDate start = LocalDate.of(year, month.getValue(), 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return expenseRepository.findAllByMonthAndYear(start, end).stream()
                .map(ExpenseMapper::toResponseDTO)
                .toList();
    }

    private BigDecimal calculateTotal(Expense expense){
        return expense.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void recalculateTotal(Expense expense){
        expense.setTotal(calculateTotal(expense));
        expenseRepository.save(expense);
    }

    public Expense getById(Long id){
        return expenseRepository.findById(id)
                .orElseThrow(ExpenseNotFound::new);
    }
}
