package dev.wu.daos;

import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;

import java.util.List;

public interface ExpenseDAO {

    Expense createNewExpense(Expense expense);

    List<Expense> getAllExpenses();

    List<Expense> getSpecificExpenses(StatusOfExpense status);

    Expense getExpenseById(int id);

    Expense updateExpense(Expense expense);

    boolean deleteExpenseById(int id);

}
