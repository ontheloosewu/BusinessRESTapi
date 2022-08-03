package dev.wu.daos;

import dev.wu.entities.Expenses;
import dev.wu.entities.StatusOfExpense;

import java.util.HashMap;
import java.util.Map;

public class ExpensesDAOLocal implements ExpensesDAO {

    private Map<Integer, Expenses> expensesMap = new HashMap();

    @Override
    public Expenses createNewExpense(Expenses expense) {
        expensesMap.put(expense.getExpenseId(), expense);
        return expense;
    }

    @Override
    public Map<Integer, Expenses> getAllExpenses() {
        return this.expensesMap;
    }

    @Override
    public Map<Integer, Expenses> getSpecificExpenses(StatusOfExpense status) {
        Map<Integer, Expenses> specificExpenseList = new HashMap();
        for (Expenses e : expensesMap.values()) {
            if (e.getStatus() == status) {
                specificExpenseList.put(e.getExpenseId(), e);
            }
        }
        return specificExpenseList;
    }

    @Override
    public Expenses getExpenseById(int id) {
        return expensesMap.get(id);
    }

    @Override
    public Expenses updateExpense(Expenses expense) {
        Expenses updatedExpense = expensesMap.put(expense.getExpenseId(), expense);
        return updatedExpense;
    }

    @Override
    public StatusOfExpense approveExpenseById(int id) {
        expensesMap.get(id).setStatus(StatusOfExpense.APPROVED);
        return expensesMap.get(id).getStatus();
    }

    @Override
    public StatusOfExpense denyExpenseById(int id) {
        expensesMap.get(id).setStatus(StatusOfExpense.DENIED);
        return expensesMap.get(id).getStatus();
    }

    @Override
    public boolean deleteExpenseById(int id) {
        Expenses result = expensesMap.remove(id);
        if (result == null) {
            return false;
        }
        return true;
    }
}
