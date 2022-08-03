package dev.wu.services;

import dev.wu.daos.ExpensesDAO;
import dev.wu.entities.Expenses;
import dev.wu.entities.StatusOfExpense;
import dev.wu.entities.TypeOfExpense;

import java.util.HashMap;
import java.util.Map;

public class ExpenseServiceImpl implements ExpenseService {

    private ExpensesDAO expensesDAO;

    public ExpenseServiceImpl(ExpensesDAO expensesDAO) {
        this.expensesDAO = expensesDAO;
    }

    @Override
    public Expenses newValidExpense(Expenses expense) {
        if(expense.getExpenseAmount() < 0) {
            throw new RuntimeException("Expense amount cannot be less than 0.");
        }
        if (expensesDAO.getAllExpenses().containsKey(expense.getExpenseId())){
            throw new RuntimeException("There already exists an expense with this ID.");
        }
        if (expense.getTypeOfExpense() != TypeOfExpense.LODGING && expense.getTypeOfExpense() != TypeOfExpense.TRAVEL
                && expense.getTypeOfExpense() != TypeOfExpense.FOOD && expense.getTypeOfExpense() != TypeOfExpense.MISC){
            throw new RuntimeException("Must specify what type of expense this is.");
        }
        Expenses newExpense = expensesDAO.createNewExpense(expense);
        return newExpense;
    }

    @Override
    public Map<Integer, Expenses> getAllExpenses() {
        return expensesDAO.getAllExpenses();
    }

    @Override
    public Map<Integer, Expenses> getSpecificExpenses(StatusOfExpense status) {
        Map<Integer, Expenses> specificExpenses = new HashMap();
        for (Expenses e : expensesDAO.getAllExpenses().values()) {
            if (e.getStatus() == status){
                specificExpenses.put(e.getExpenseId(), e);
            }
        }
        return specificExpenses;
    }

    @Override
    public Expenses getExpenseById(int id) {
        Expenses theExpense = expensesDAO.getExpenseById(id);
        return theExpense;
    }

    @Override
    public Expenses updateExpenseById(Expenses expense) {
        Expenses updatedExpense = expensesDAO.updateExpense(expense);
        return updatedExpense;
    }

    @Override
    public StatusOfExpense approveExpenseById(int id) {
        StatusOfExpense approvedExpense = expensesDAO.approveExpenseById(id);
        return approvedExpense;
    }

    @Override
    public StatusOfExpense denyExpenseById(int id) {
        StatusOfExpense deniedExpense = expensesDAO.denyExpenseById(id);
        return deniedExpense;
    }

    @Override
    public boolean deleteExpenseById(int id) {
        return this.expensesDAO.deleteExpenseById(id);
    }
}
