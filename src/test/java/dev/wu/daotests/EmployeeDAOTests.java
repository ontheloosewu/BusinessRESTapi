package dev.wu.daotests;

import dev.wu.daos.EmployeeDAOLocal;
import dev.wu.entities.Employee;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDAOTests {

    static EmployeeDAOLocal employeeDao = new EmployeeDAOLocal();

    @Test
    @Order(1)
    void create_new_employee_test(){
        Employee employee = new Employee("Tim", "Wu", 101);
        Employee addedEmployee = employeeDao.createEmployee(employee);
        Assertions.assertNotEquals(100, addedEmployee.getEmployeeId());
    }

    @Test
    @Order(2)
    void get_employee_by_id_test(){
        Employee employee = employeeDao.getEmployeeById(101);
        Assertions.assertEquals("TimWu", employee.getfName() + employee.getlName());
    }

    @Test
    @Order(3)
    void get_all_employees_test(){
        Assertions.assertEquals(1, employeeDao.getAllEmployees().size());
    }

    @Test
    @Order(3)
    void update_employee_by_id_test(){
        Employee newEmployee = new Employee("Timothy", "Wu", 101);
        employeeDao.updateEmployee(newEmployee);
        Employee updatedEmployee = employeeDao.getEmployeeById(101);
        System.out.println(updatedEmployee.toString());
        Assertions.assertEquals("TimothyWu", updatedEmployee.getfName() + updatedEmployee.getlName());
    }

    @Test
    @Order(4)
    void remove_employee_by_id_test(){
        boolean result = employeeDao.removeEmployeeById(101);
        Assertions.assertTrue(result);
    }
}
