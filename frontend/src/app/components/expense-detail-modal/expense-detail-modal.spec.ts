import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseDetailModal } from './expense-detail-modal';

describe('ExpenseDetailModal', () => {
  let component: ExpenseDetailModal;
  let fixture: ComponentFixture<ExpenseDetailModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpenseDetailModal],
    }).compileComponents();

    fixture = TestBed.createComponent(ExpenseDetailModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
