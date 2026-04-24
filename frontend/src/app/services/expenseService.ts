import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {

  private apiUrl = 'http://localhost:8080/expenses';

  constructor(private http: HttpClient){}

  findAll() {
    return this.http.get(this.apiUrl);
  }

  findExpenseById(id: number) {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  findRecentExpenses(){
    return this.http.get(this.apiUrl + '/recent');
  }

  findAllExpensesByMonthAndYear(month: string, year: number){
    return this.http.get(this.apiUrl + `/by-month?month=${month}&year=${year}`);
  }

  getMonthSummary(month: string, year: number) {
    return this.http.get(this.apiUrl + `/summary?month=${month}&year=${year}`);
  }

  compareTwoMonths(month1: string, year1: number, month2: string, year2: number){
    const params = new HttpParams()
          .set('firstMonth', month1.toUpperCase())
          .set('firstYear', year1)
          .set('secondMonth', month2.toUpperCase())
          .set('secondYear', year2);

    return this.http.get<any>(this.apiUrl + '/compare', { params })
  }

  createExpense(expense: any) {
    return this.http.post(this.apiUrl, expense);
  }

  editExpense(id: number, expense: any) {
    return this.http.patch(`${this.apiUrl}/${id}`, expense)
  }

  deleteExpense(id: number){
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
