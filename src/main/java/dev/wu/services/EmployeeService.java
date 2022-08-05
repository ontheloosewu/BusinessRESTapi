package dev.wu.services;

import dev.wu.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee newValidEmployee(Employee employee);

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();

    Employee updatedEmployee(Employee employee);

    boolean employeeRemoved(int id);
}
