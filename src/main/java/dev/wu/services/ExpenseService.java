package dev.wu.services;

import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;

import java.util.List;

public interface ExpenseService {

    Expense newValidExpense(Expense expense);

    List<Expense> getAllExpenses();

    List<Expense> getSpecificExpenses(StatusOfExpense status);

    Expense getExpenseById(int id);

    Expense updateExpense(Expense expense, StatusOfExpense status);

    boolean expenseDeleted(int id);
}
