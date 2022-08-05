package dev.wu.daotests;

import dev.wu.daos.EmployeeDAO;
import dev.wu.daos.EmployeeDAOPostgres;
import dev.wu.entities.Employee;
import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDAOTests {

    static EmployeeDAO employeeDao = new EmployeeDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "create table employee(\n" +
                    "\temployee_id serial primary key,\n" +
                    "\tfirst_name varchar(100) not null,\n" +
                    "\tlast_name varchar(100) not null\n" +
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
            String sql = "drop table employee";
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_new_employee_test() {
        Employee employee = new Employee("Tim", "Wu", 1);
        Employee addedEmployee = employeeDao.createEmployee(employee);
        Assertions.assertNotEquals(100, addedEmployee.getEmployeeId());
        System.out.println(employee);
    }

    @Test
    @Order(2)
    void get_employee_by_id_test() {
        Employee employee = employeeDao.getEmployeeById(1);
        Assertions.assertEquals("TimWu", employee.getfName() + employee.getlName());
        System.out.println(employee);
    }

    @Test
    @Order(3)
    void get_all_employees_test() {
        Employee employee2 = new Employee("Teemo", "Wu", 2);
        Employee employee3 = new Employee("Timmo", "Wu", 3);
        Employee employee4 = new Employee("Tummo", "Wu", 4);

        employeeDao.createEmployee(employee2);
        employeeDao.createEmployee(employee3);
        employeeDao.createEmployee(employee4);
        Assertions.assertEquals(4, employeeDao.getAllEmployees().size());
        System.out.println(employeeDao.getAllEmployees());
    }

    @Test
    @Order(3)
    void update_employee_by_id_test() {
        Employee newEmployee = new Employee("Timothy", "Wu", 1);
        employeeDao.updateEmployee(newEmployee);
        Employee updatedEmployee = employeeDao.getEmployeeById(1);
        Assertions.assertEquals("TimothyWu", updatedEmployee.getfName() + updatedEmployee.getlName());
        System.out.println(employeeDao.getAllEmployees());
    }

    @Test
    @Order(4)
    void remove_employee_by_id_test() {
        boolean result = employeeDao.removeEmployeeById(1);
        Assertions.assertTrue(result);
        System.out.println(employeeDao.getAllEmployees());
    }
}
