package dev.wu.daos;

import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAOLocal implements ExpenseDAO {

    private final Map<Integer, Expense> expensesMap = new HashMap();

    @Override
    public Expense createNewExpense(Expense expense) {
        expensesMap.put(expense.getExpenseId(), expense);
        return expense;
    }

    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> allExpens = new ArrayList();
        for (Expense e : expensesMap.values()) {
            allExpens.add(e);
        }
        return allExpens;
    }

    @Override
    public List<Expense> getSpecificExpenses(StatusOfExpense status) {
        List<Expense> specificExpenseList = new ArrayList();
        for (Expense e : expensesMap.values()) {
            if (e.getStatus() == status) {
                specificExpenseList.add(e);
            }
        }
        return specificExpenseList;
    }

    @Override
    public Expense getExpenseById(int id) {
        return expensesMap.get(id);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense updatedExpense = expensesMap.put(expense.getExpenseId(), expense);
        return updatedExpense;
    }

    @Override
    public boolean deleteExpenseById(int id) {
        Expense result = expensesMap.remove(id);
        return result != null;
    }
}
