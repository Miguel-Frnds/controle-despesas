import { Component, Input, Output, EventEmitter, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { MemberService } from '../../services/memberService';
import { ExpenseService } from '../../services/expenseService';

@Component({
  selector: 'app-expense-modal',
  imports: [NgFor, FormsModule, NgIf, CurrencyPipe],
  templateUrl: './expense-modal.html',
  styleUrl: './expense-modal.css',
})
export class ExpenseModal implements OnChanges, OnInit{

  constructor(
    private memberService: MemberService,
    private expenseService: ExpenseService
  ){}

  title = '';
  expenseDate = '';
  category = '';
  memberId = '';
  items = [{name: '', quantity: 1, unitPrice: 0.00}]
  activeMembers: any[] = [];
  categories = ['FOOD'];

  @Input() expense: any = null;

  @Input() isOpen = false;
  @Output() closed = new EventEmitter();
  @Output() saved = new EventEmitter();

  ngOnChanges(changes: SimpleChanges){
    if(changes['expense']){
        if(this.expense){
            this.title = this.expense.title;
            this.expenseDate = this.expense.expenseDate;
            this.category = this.expense.category;
            const member = this.activeMembers.find(
              m => m.name === this.expense.memberName
            );

            if(member){
              this.memberId = String(member.id);
            }
            this.items = this.expense.items;
        } else {
            this.title = '';
            this.expenseDate = '';
            this.category = '';
            this.memberId = '';
            this.items = [{name: '', quantity: 1, unitPrice: 0.00}];
        }
    }
  }

  categoryLabels: Record<string, string> = {
    'FOOD': 'Alimentação'
  };

  ngOnInit(): void{
    this.loadMembers();
  }

  fechar(){
      this.closed.emit();
  }

  addItems(){
    this.items.push({name: '', quantity: 1, unitPrice: 0.00});
  }

  get totalExpense(): number {
    return this.items.reduce((total, item) => 
            total + (item.quantity * item.unitPrice), 0);
  }

  removeItem(index: number){
    this.items.splice(index, 1);
  }

  loadMembers(){
    this.memberService.findMembers(true).subscribe((data: any) => {
        this.activeMembers = data;
    })
  }

  saveExpense(){
    const expense = {
      title: this.title,
      expenseDate: this.expenseDate,
      category: this.category,
      memberId: Number(this.memberId),
      items: this.items
    };

    if(!this.title || !this.expenseDate || !this.category || !this.memberId){
        alert('Preencha todos os campos!');
        return;
    }

    const itemsValidos = this.items.every(item => item.name && item.quantity > 0 && item.unitPrice > 0);
    if(!itemsValidos){
        alert('Preencha todos os campos dos itens!');
        return;
    }

    if(this.expense) {
      this.expenseService.editExpense(this.expense.id, expense).subscribe(() => {
        this.saved.emit();
        this.closed.emit();
      });
    } else {
        this.expenseService.createExpense(expense).subscribe(() => {
          this.saved.emit();
          this.closed.emit();
      });
    }
  }
}
