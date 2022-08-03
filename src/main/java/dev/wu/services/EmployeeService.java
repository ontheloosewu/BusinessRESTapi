package dev.wu.services;

import dev.wu.entities.Employee;

import java.util.Map;

public interface EmployeeService {

    Employee newValidEmployee(Employee employee);

    Employee getEmployeeById(int id);

    Map<Integer, Employee> getAllEmployees();

    Employee updatedEmployee(Employee employee);

    boolean employeeRemoved(int id);
}
