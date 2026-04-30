import { CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { Component, Input, Output, EventEmitter} from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-expense-detail-modal',
  imports: [NgFor, CurrencyPipe, FormsModule, NgIf],
  templateUrl: './expense-detail-modal.html',
  styleUrl: './expense-detail-modal.css',
})
export class ExpenseDetailModal {

  @Input() expense: any = null;
  @Input() isOpen = false;
  @Output() closed = new EventEmitter();

  fechar(){
    this.closed.emit();
  }

  categoryLabels: Record<string, string> = {
    'FOOD': 'Alimentação'
  };

  get totalExpense(): number {
    if(!this.expense) return 0;
    return this.expense.items.reduce((total: number, item: any) => 
        total + (item.quantity * item.unitPrice), 0);
}
}
