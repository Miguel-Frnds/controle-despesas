import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/homeComponent';
import { Expenses } from './components/expenses/expenses';
import { Members } from './components/members/members';
import { Compare } from "./components/compare/compare";

export const routes: Routes = [
    {
        path: "",
        redirectTo: "home",
        pathMatch: "full"
    },
    {
        path: "home",
        component: HomeComponent,
    },
    {
        path: "expenses",
        component: Expenses,
    },
    {
        path: "members",
        component: Members,
    },
    {
        path: "compare",
        component: Compare
    }
];
