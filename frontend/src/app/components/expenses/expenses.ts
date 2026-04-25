import { Component, OnInit, ChangeDetectorRef, Input } from '@angular/core';
import { CurrencyPipe, DatePipe, NgFor } from '@angular/common';
import { ExpenseService } from '../../services/expenseService';
import { ExpenseModal } from '../expense-modal/expense-modal';
import { ExpenseDetailModal } from '../expense-detail-modal/expense-detail-modal';

@Component({
  selector: 'app-expenses',
  imports: [NgFor, CurrencyPipe, DatePipe, ExpenseModal, ExpenseDetailModal],
  templateUrl: './expenses.html',
  styleUrl: './expenses.css',
})
export class Expenses implements OnInit {

  constructor(
    private expenseService: ExpenseService,
    private cdr: ChangeDetectorRef
  ) {}
  
  expenses: any[] = [];
  expensesCount: number = 0;
  today = new Date();
  showModal = false;

  selectedExpense: any = null;
  showExpenseModal = false;

  get totalExpense(): number {
    return this.expenses.reduce((total, expense) => 
            total + expense.total, 0
    );
  }

  loadExpenses(){
    const month = this.today.toLocaleString('en-US', {month : 'long'}).toUpperCase();
    const year = this.today.getFullYear();
    this.expenseService.findAllExpensesByMonthAndYear(month, year).subscribe((data: any) => {
      this.expenses = data
          .sort((a: any, b: any) => new Date(b.expenseDate).getTime() - new Date(a.expenseDate).getTime());
      this.expensesCount = this.expenses.length;
      this.cdr.detectChanges();
    });
  }

  ngOnInit(): void{
      this.loadExpenses();
  }

  previousMonth(){
    this.today = new Date(this.today.getFullYear(), this.today.getMonth() - 1, 1)
    this.loadExpenses();
  }

  nextMonth(){
    this.today = new Date(this.today.getFullYear(), this.today.getMonth() + 1, 1)
    this.loadExpenses();
  }

  deleteExpense(id :number){
    if(confirm('Tem certeza de que deseja excluir essa despesa?')){
      this.expenseService.deleteExpense(id).subscribe(() => {
        this.loadExpenses();
      });
    }
  }

  openExpense(id: number) {
    this.expenseService.findExpenseById(id).subscribe((data: any) => {
      this.selectedExpense = data;
      this.showExpenseModal = true;
      this.cdr.detectChanges();
    });
  }

  editExpense(id: number) {
    this.expenseService.findExpenseById(id).subscribe((data: any) => {
      this.selectedExpense = data;
      this.showModal = true;
      this.cdr.detectChanges();
    })
  }

  openCreateModal() {
    this.selectedExpense = null;
    this.showModal = true;
  }

  closeExpenseModal() {
    this.showModal = false;
    this.selectedExpense = null;
  }

  closeExpenseDetailModal() {
    this.showExpenseModal = false;
    this.selectedExpense = null;
  }

  onExpenseSaved() {
    this.showModal = false;
    this.selectedExpense = null;
    this.loadExpenses();
  }
}
