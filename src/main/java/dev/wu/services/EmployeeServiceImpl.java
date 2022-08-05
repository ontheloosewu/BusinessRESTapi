package dev.wu.services;

import dev.wu.daos.EmployeeDAO;
import dev.wu.entities.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee newValidEmployee(Employee employee) {
        if (employee.getfName() == null || employee.getlName() == null) {
            throw new RuntimeException("Employee must have a first and/or last name.");
        }
        return this.employeeDAO.createEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return this.employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getAllEmployees();
    }

    @Override
    public Employee updatedEmployee(Employee employee) {
        if (employee.getfName() == null || employee.getlName() == null) {
            throw new RuntimeException("Employee must have a first and/or last name.");
        }
        return this.employeeDAO.updateEmployee(employee);
    }

    @Override
    public boolean employeeRemoved(int id) {
        return this.employeeDAO.removeEmployeeById(id);
    }
}
