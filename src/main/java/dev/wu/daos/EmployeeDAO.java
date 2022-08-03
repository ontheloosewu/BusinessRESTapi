package dev.wu.daos;

import dev.wu.entities.Employee;

import java.util.Map;

public interface EmployeeDAO {
    // CRUD

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    Map<Integer, Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    boolean removeEmployeeById(int id);
}
