package dev.wu.entities;

public class Employee {

    private String fName;
    private String lName;
    private int employeeId;

    public Employee(){

    }

    public Employee(String fName, String lName, int employeeId) {
        this.fName = fName;
        this.lName = lName;
        this.employeeId = employeeId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }
}
