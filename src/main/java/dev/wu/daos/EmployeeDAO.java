package dev.wu.daos;

import dev.wu.entities.Employee;

import java.util.List;

public interface EmployeeDAO {
    // CRUD

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    boolean removeEmployeeById(int id);
}
