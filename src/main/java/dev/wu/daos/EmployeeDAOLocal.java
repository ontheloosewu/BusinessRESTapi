package dev.wu.daos;

import dev.wu.entities.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAOLocal implements EmployeeDAO {

    private final Map<Integer, Employee> employees = new HashMap();

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
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = new ArrayList();
        for (Employee e : employees.values()) {
            allEmployees.add(e);
        }
        return allEmployees;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee updatedEmployee = employees.put(employee.getEmployeeId(), employee);
        return updatedEmployee;
    }

    @Override
    public boolean removeEmployeeById(int id) {
        Employee removedEmployee = employees.remove(id);
        return removedEmployee != null;
    }
}
