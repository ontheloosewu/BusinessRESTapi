package dev.wu.services;

import dev.wu.entities.Expenses;
import dev.wu.entities.StatusOfExpense;

import java.util.Map;

public interface ExpenseService {

    Expenses newValidExpense(Expenses expense);

    Map<Integer, Expenses> getAllExpenses();

    Map<Integer, Expenses> getSpecificExpenses(StatusOfExpense status);

    Expenses getExpenseById(int id);

    Expenses updateExpenseById(Expenses expense);

    StatusOfExpense approveExpenseById(int id);

    StatusOfExpense denyExpenseById(int id);

    boolean deleteExpenseById(int id);
}
