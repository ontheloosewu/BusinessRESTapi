package dev.wu.daos;

import dev.wu.entities.Employee;
import dev.wu.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgres implements EmployeeDAO {
    @Override
    public Employee createEmployee(Employee employee) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into employee values (default, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, employee.getfName());
            preparedStatement.setString(2, employee.getlName());

            preparedStatement.execute();

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from employee where employee_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Employee employee = new Employee();
            employee.setEmployeeId(rs.getInt("employee_id"));
            employee.setfName(rs.getString("first_name"));
            employee.setlName(rs.getString("last_name"));

            preparedStatement.execute();

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from employee";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Employee> employeeList = new ArrayList();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setfName(rs.getString("first_name"));
                employee.setlName(rs.getString("last_name"));
                employeeList.add(employee);
            }
            return employeeList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "update employee set first_name = ?, last_name = ? where employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, employee.getfName());
            preparedStatement.setString(2, employee.getlName());
            preparedStatement.setInt(3, employee.getEmployeeId());

            preparedStatement.executeUpdate();
            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean removeEmployeeById(int id) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "delete from employee where employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
