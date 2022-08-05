package dev.wu.daotests;

import dev.wu.daos.ExpenseDAO;
import dev.wu.daos.ExpenseDAOPostgres;
import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import dev.wu.entities.TypeOfExpense;
import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDAOTests {

    static ExpenseDAO expenseDAO = new ExpenseDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "create table expense(\n" +
                    "expense_id serial primary key,\n" +
                    "amount decimal(10,2),\n" +
                    "type_of_expense varchar(20),\n" +
                    "status varchar(20),\n" +
                    "issuer_id int references employee(employee_id)\n" +
                    ");";

            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll // runs after the last test finishes
    static void teardown() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "drop table expense";
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_new_expense_test() {
        Expense expense = new Expense(1, 1, 1000.50, TypeOfExpense.FOOD);
        expenseDAO.createNewExpense(expense);
        Assertions.assertNotEquals(0, expense.getExpenseId());
    }

    @Test
    @Order(2)
    void get_all_expenses_test() {
        Assertions.assertEquals(1, expenseDAO.getAllExpenses().size());
    }

    @Test
    @Order(3)
    void get_all_expenses_by_status_test() {
        Assertions.assertEquals(1, expenseDAO.getSpecificExpenses(StatusOfExpense.PENDING).size());
    }

    @Test
    @Order(4)
    void get_expense_by_id_test() {
        Expense expense = expenseDAO.getExpenseById(1);
        Assertions.assertEquals(1, expense.getIssuerId());
    }

    @Test
    @Order(5)
    void update_expense_test() {
        Expense newExpense = new Expense(1, 1, 6.69, TypeOfExpense.MISC);
        expenseDAO.updateExpense(newExpense);
        Assertions.assertEquals(6.69, expenseDAO.getExpenseById(1).getExpenseAmount());
    }

    @Test
    @Order(8)
    void delete_expense_by_id_test() {
        boolean result = expenseDAO.deleteExpenseById(101);
        Assertions.assertTrue(result);
    }
}
