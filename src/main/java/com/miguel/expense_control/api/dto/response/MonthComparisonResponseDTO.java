package com.miguel.expense_control.api.dto.response;

import java.math.BigDecimal;
import java.time.Month;

public record MonthComparisonResponseDTO(
        Month firstMonth,
        BigDecimal firstMonthTotal,
        int firstMonthExpenseCount,
        Month secondMonth,
        BigDecimal secondMonthTotal,
        int secondMonthExpenseCount,
        BigDecimal difference,
        Month highestSpendingMonth
) {
}
