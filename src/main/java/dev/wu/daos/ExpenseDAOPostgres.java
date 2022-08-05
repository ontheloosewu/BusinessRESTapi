package dev.wu.daos;

import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import dev.wu.entities.TypeOfExpense;
import dev.wu.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {
    @Override
    public Expense createNewExpense(Expense expense) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into expense values (default, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, expense.getExpenseAmount());
            preparedStatement.setString(2, expense.getTypeOfExpense().toString());
            preparedStatement.setString(3, StatusOfExpense.PENDING.toString());
            preparedStatement.setInt(4, expense.getIssuerId());

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys(); // returns the id that was created
            rs.next(); // you have to move the cursor to the first valid record

            int generatedKey = rs.getInt("expense_id");
            expense.setExpenseId(generatedKey);

            return expense;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Expense> getAllExpenses() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from expense";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Expense> expenseList = new ArrayList();
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setExpenseAmount(rs.getDouble("amount"));
                expense.setTypeOfExpense(TypeOfExpense.valueOf(rs.getString("type_of_expense")));
                expense.setStatus(StatusOfExpense.valueOf(rs.getString("status")));
                expense.setIssuerId(rs.getInt("issuer_id"));
                expenseList.add(expense);
            }
            return expenseList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Expense> getSpecificExpenses(StatusOfExpense status) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from expense where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status.toString());

            ResultSet rs = preparedStatement.executeQuery();

            List<Expense> expenseList = new ArrayList();
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setExpenseAmount(rs.getDouble("amount"));
                expense.setTypeOfExpense(TypeOfExpense.valueOf(rs.getString("type_of_expense")));
                expense.setStatus(StatusOfExpense.valueOf(rs.getString("status")));
                expense.setIssuerId(rs.getInt("issuer_id"));
                expenseList.add(expense);
            }
            return expenseList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense getExpenseById(int id) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from expense where expense_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Expense expense = new Expense();
            expense.setExpenseId(rs.getInt("expense_id"));
            expense.setExpenseAmount(rs.getDouble("amount"));
            expense.setTypeOfExpense(TypeOfExpense.valueOf(rs.getString("type_of_expense")));
            expense.setStatus(StatusOfExpense.valueOf(rs.getString("status")));
            expense.setIssuerId(rs.getInt("issuer_id"));

            preparedStatement.execute();

            return expense;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense updateExpense(Expense expense) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "update expense set amount = ?, type_of_expense = ?, status = ?, issuer_id = ? where expense_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, expense.getExpenseAmount());
            preparedStatement.setString(2, expense.getTypeOfExpense().toString());
            preparedStatement.setString(3, expense.getStatus().toString());
            preparedStatement.setInt(4, expense.getIssuerId());
            preparedStatement.setInt(5, expense.getExpenseId());

            preparedStatement.executeUpdate();

            return expense;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteExpenseById(int id) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "delete from expense where expense_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
