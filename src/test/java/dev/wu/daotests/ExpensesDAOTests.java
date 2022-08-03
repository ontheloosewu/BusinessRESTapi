package dev.wu.daotests;

import dev.wu.daos.ExpensesDAOLocal;
import dev.wu.entities.Expenses;
import dev.wu.entities.StatusOfExpense;
import dev.wu.entities.TypeOfExpense;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpensesDAOTests {

    static ExpensesDAOLocal expensesDAOLocal = new ExpensesDAOLocal();

    @Test
    @Order(1)
    void create_new_expense_test(){
        Expenses expense = new Expenses(1, 101, 1000.50, TypeOfExpense.LODGING);
        expensesDAOLocal.createNewExpense(expense);
        Expenses expenseTwo = new Expenses(2, 102, 69.69, TypeOfExpense.FOOD);
        expensesDAOLocal.createNewExpense(expenseTwo);
        Assertions.assertEquals(expensesDAOLocal.getExpenseById(101), expense);
        Assertions.assertEquals(expensesDAOLocal.getExpenseById(102), expenseTwo);
    }

    @Test
    @Order(2)
    void get_all_expenses_test(){
        Assertions.assertEquals(2, expensesDAOLocal.getAllExpenses().size());
    }

    @Test
    @Order(3)
    void get_all_expenses_by_status_test(){
        Assertions.assertEquals(2, expensesDAOLocal.getSpecificExpenses(StatusOfExpense.PENDING).size());
    }

    @Test
    @Order(4)
    void get_expense_by_id_test(){
        Expenses expense = expensesDAOLocal.getExpenseById(101);
        Assertions.assertEquals(1, expense.getIssuerId());
    }

    @Test
    @Order(5)
    void update_expense_test(){
        Expenses newExpense = new Expenses(1, 102, 6.69, TypeOfExpense.MISC);
        expensesDAOLocal.updateExpense(newExpense);
        Assertions.assertEquals(6.69, expensesDAOLocal.getExpenseById(102).getExpenseAmount());
    }

    @Test
    @Order(6)
    void approve_expense_by_id_test(){
        expensesDAOLocal.approveExpenseById(101);
        Assertions.assertEquals(StatusOfExpense.APPROVED, expensesDAOLocal.getExpenseById(101).getStatus());
    }

    @Test
    @Order(7)
    void deny_expense_by_id_test(){
        expensesDAOLocal.denyExpenseById(102);
        Assertions.assertEquals(StatusOfExpense.DENIED, expensesDAOLocal.getExpenseById(102).getStatus());
    }

    @Test
    @Order(8)
    void delete_expense_by_id_test(){
        boolean result = expensesDAOLocal.deleteExpenseById(101);
        Assertions.assertTrue(result);
    }
}
