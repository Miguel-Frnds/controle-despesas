import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ExpenseService } from '../../services/expenseService';
import { NgFor, CurrencyPipe, DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { ExpenseModal } from '../expense-modal/expense-modal';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgFor, CurrencyPipe, DatePipe, ExpenseModal],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class HomeComponent implements OnInit {
  constructor(
    private expenseService: ExpenseService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  expenses: any[] = [];
  monthTotal: number = 0;
  expenseCount: number = 0;
  today = new Date();
  showModal = false;
  selectedExpense: any = null;

  loadExpenses(){
    const month = this.today.toLocaleString('en-US', { month: 'long' }).toUpperCase();
    const year = this.today.getFullYear();

    this.expenseService.findAllExpensesByMonthAndYear(month, year).subscribe((data: any) => {
      this.expenses = data
          .sort((a: any, b: any) => new Date(b.expenseDate).getTime() - new Date(a.expenseDate).getTime())
          .slice(0, 10);
      this.expenseCount = data.length;
      this.cdr.detectChanges();
    });
  }


  ngOnInit(): void {
    this.loadExpenses();

    const month = this.today.toLocaleString('en-US', { month: 'long' }).toUpperCase();
    const year = this.today.getFullYear();

    this.expenseService.getMonthSummary(month, year).subscribe({
      next: (data: any) => {
        this.monthTotal = data;
        this.cdr.detectChanges();
      }
    });
  }

  goToExpenses(){
    this.router.navigate(['/expenses'])
  }
}