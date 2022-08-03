package dev.wu.daos;

import dev.wu.entities.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeeDAOLocal implements EmployeeDAO {

    private Map<Integer, Employee> employees = new HashMap();

    @Override
    public Employee createEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
        return employee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employees.get(id);
    }

    @Override
    public Map<Integer, Employee> getAllEmployees() {
        return this.employees;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee updatedEmployee = employees.put(employee.getEmployeeId(), employee);
        return updatedEmployee;
    }

    @Override
    public boolean removeEmployeeById(int id) {
        Employee removedEmployee = employees.remove(id);
        if (removedEmployee == null) {
            return false;
        }
        return true;
    }
}
