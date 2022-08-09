package dev.wu.mocktests;

import dev.wu.daos.EmployeeDAO;
import dev.wu.entities.Employee;
import dev.wu.services.EmployeeService;
import dev.wu.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MockitoTests {

    @Test
    void new_employees_must_have_first_and_last_name(){
        EmployeeDAO employeeDAO = Mockito.mock(EmployeeDAO.class);
        Employee employee = new Employee("Timmo", "Wu", 1);
        Employee employee2 = new Employee("VOID", "", 2);
        Mockito.when(employeeDAO.createEmployee(employee)).thenReturn(employee);
        Mockito.when(employeeDAO.createEmployee(employee2)).thenReturn(employee);
        EmployeeService employeeService = new EmployeeServiceImpl(employeeDAO);

        Assertions.assertEquals(employee, employeeService.newValidEmployee(employee));
        System.out.println("Passed first test");
        Assertions.assertThrows(RuntimeException.class, () -> {
            employeeService.newValidEmployee(employee2);
        });
        System.out.println("Passed second test");
    }
}
