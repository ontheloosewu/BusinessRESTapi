package dev.wu.services;

import dev.wu.daos.ExpenseDAO;
import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import dev.wu.entities.TypeOfExpense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Expense newValidExpense(Expense expense) {
        if (expense.getExpenseAmount() < 0) {
            throw new RuntimeException("Expense amount cannot be less than 0.");
        }
        if (expense.getTypeOfExpense() != TypeOfExpense.LODGING && expense.getTypeOfExpense() != TypeOfExpense.TRAVEL
                && expense.getTypeOfExpense() != TypeOfExpense.FOOD && expense.getTypeOfExpense() != TypeOfExpense.MISC) {
            throw new RuntimeException("Must specify what type of expense this is.");
        }

        return expenseDAO.createNewExpense(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    public List<Expense> getSpecificExpenses(StatusOfExpense status) {
        List<Expense> specificExpenses = new ArrayList();
        for (Expense e : expenseDAO.getAllExpenses()) {
            if (e.getStatus() == status) {
                specificExpenses.add(e);
            }
        }
        return specificExpenses;
    }

    @Override
    public Expense getExpenseById(int id) {
        Expense theExpense = expenseDAO.getExpenseById(id);
        return theExpense;
    }

    @Override
    public Expense updateExpense(Expense expense, StatusOfExpense status) {
        if (expense.getExpenseAmount() < 0) {
            throw new RuntimeException("Expense amount cannot be less than 0.");
        }
        if (expense.getTypeOfExpense() != TypeOfExpense.LODGING && expense.getTypeOfExpense() != TypeOfExpense.TRAVEL
                && expense.getTypeOfExpense() != TypeOfExpense.FOOD && expense.getTypeOfExpense() != TypeOfExpense.MISC) {
            throw new RuntimeException("Must specify what type of expense this is.");
        }
        if (expense.getStatus() == StatusOfExpense.APPROVED || expense.getStatus() == StatusOfExpense.DENIED) {
            throw new RuntimeException("Cannot update expense; has already been approved/denied.");
        }
        expense.setStatus(status);
        return expenseDAO.updateExpense(expense);
    }

    @Override
    public boolean expenseDeleted(int id) {
        if (getExpenseById(id).getStatus() == StatusOfExpense.APPROVED || getExpenseById(id).getStatus() == StatusOfExpense.DENIED) {
            throw new RuntimeException("Cannot delete expense; has already been approved/denied.");
        }
        return this.expenseDAO.deleteExpenseById(id);
    }
}
