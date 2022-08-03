package dev.wu.services;

import dev.wu.daos.EmployeeDAO;
import dev.wu.entities.Employee;

import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee newValidEmployee(Employee employee) {
        if (employeeDAO.getAllEmployees().containsKey(employee.getEmployeeId())) {
            throw new RuntimeException("There already exists an employee with this ID");
        }
        Employee createdEmployee = employeeDAO.createEmployee(employee);
        return createdEmployee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return this.employeeDAO.getEmployeeById(id);
    }

    @Override
    public Map<Integer, Employee> getAllEmployees() {
        return this.employeeDAO.getAllEmployees();
    }

    @Override
    public Employee updatedEmployee(Employee employee) {
        return this.employeeDAO.updateEmployee(employee);
    }

    @Override
    public boolean employeeRemoved(int id) {
        return this.employeeDAO.removeEmployeeById(id);
    }
}
