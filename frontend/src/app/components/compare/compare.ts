import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ExpenseService } from '../../services/expenseService';
import { CurrencyPipe, NgClass } from '@angular/common';

export interface MonthComparisionResponseDTO {
  firstMonth: string;
  firstMonthTotal: number;
  firstMonthExpenseCount: number;
  secondMonth: string;
  secondMonthTotal: number;
  secondMonthExpenseCount: number;
  difference: number;
  highestSpendingMonth: string;
}

@Component({
  selector: 'app-compare',
  imports: [FormsModule, CurrencyPipe, NgClass],
  templateUrl: './compare.html',
  styleUrl: './compare.css',
})
export class Compare {

  constructor(
    private expenseService: ExpenseService,
    private cdr: ChangeDetectorRef
  ){}

  showGraph = false;

  month1 = 'Janeiro';
  month2 = 'Fevereiro';
  months = [
    'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio',
    'Junho', 'Julho', 'Agosto', 'Setembro',
    'Outubro', 'Novembro', 'Dezembro'
  ]

  monthMap: { [key: string]: string } = {
    'Janeiro': 'JANUARY', 'Fevereiro': 'FEBRUARY', 'Março': 'MARCH',
    'Abril': 'APRIL', 'Maio': 'MAY', 'Junho': 'JUNE',
    'Julho': 'JULY', 'Agosto': 'AUGUST', 'Setembro': 'SEPTEMBER',
    'Outubro': 'OCTOBER', 'Novembro': 'NOVEMBER', 'Dezembro': 'DECEMBER'
  };

  private monthTranslation: { [key: string]: string } = {
    'January': 'Janeiro', 'February': 'Fevereiro', 'March': 'Março',
    'April': 'Abril', 'May': 'Maio', 'June': 'Junho',
    'July': 'Julho', 'August': 'Agosto', 'September': 'Setembro',
    'October': 'Outubro', 'November': 'Novembro', 'December': 'Dezembro'
  };

  years: number[] = [];
  year1 = new Date().getFullYear();
  year2 = new Date().getFullYear();

  comparison: MonthComparisionResponseDTO | null = null;

  ngOnInit() {
    const currentYear = new Date().getFullYear();

    for(let i = 0 ; i < 5; i++) {
      this.years.push(currentYear - i);
    }
  }

  compareMonths(){
    this.expenseService.compareTwoMonths(
      this.monthMap[this.month1], this.year1,
      this.monthMap[this.month2], this.year2)
      .subscribe((data: MonthComparisionResponseDTO) => {
      this.comparison = data;
      this.showGraph = true;
      this.cdr.detectChanges();
    });
  }

  formatMonth(month: any): string {
    const str = String(month).charAt(0) + String(month).slice(1).toLowerCase();
    return this.monthTranslation[str] ?? str;
  }

  getDifferencePercent(): string {
    const first = this.comparison?.firstMonthTotal ?? 0;
    const second = this.comparison?.secondMonthTotal ?? 0;

    if(first === 0 ) return '0%';

    const percent = ((second - first) / first) * 100;
    const label = percent > 0 ? 'Aumento de' : 'Redução de';
    return  `${label} ${Math.abs(percent).toFixed(1)}%`;
  }

  getDiffExpense(): number {
    const first = this.comparison?.firstMonthExpenseCount ?? 0;
    const second = this.comparison?.secondMonthExpenseCount ?? 0;

    return Math.abs(second - first);
  }

  getExpenseCountDiffLabel(): string {
    const first = this.comparison?.firstMonthExpenseCount ?? 0;
    const second = this.comparison?.secondMonthExpenseCount ?? 0;

    return second > first ? 'Mais transações' : 'Menos transações';
  }

  isIncrease(): boolean {
    return (this.comparison?.difference ?? 0) > 0;
  }
}
