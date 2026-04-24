package com.miguel.expense_control.api.dto.response;

import java.math.BigDecimal;
import java.time.Month;

public record MonthComparisonResponseDTO(
        String firstMonth,
        BigDecimal firstMonthTotal,
        int firstMonthExpenseCount,
        String secondMonth,
        BigDecimal secondMonthTotal,
        int secondMonthExpenseCount,
        BigDecimal difference,
        String highestSpendingMonth
) {
}
