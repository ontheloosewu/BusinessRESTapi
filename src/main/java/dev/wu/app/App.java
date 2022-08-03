package dev.wu.app;

import dev.wu.daos.EmployeeDAOLocal;
import dev.wu.daos.ExpensesDAOLocal;
import dev.wu.handlers.employee.*;
import dev.wu.handlers.expense.*;
import dev.wu.services.EmployeeService;
import dev.wu.services.EmployeeServiceImpl;
import dev.wu.services.ExpenseService;
import dev.wu.services.ExpenseServiceImpl;
import io.javalin.Javalin;

public class App {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOLocal());
    public static ExpenseService expenseService = new ExpenseServiceImpl(new ExpensesDAOLocal());

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // employee handlers
        CreateEmployeeHandler createEmployeeHandler = new CreateEmployeeHandler();
        GetAllEmployeeHandler getAllEmployeeHandler = new GetAllEmployeeHandler();
        GetEmployeeByIdHandler getEmployeeByIdHandler = new GetEmployeeByIdHandler();
        UpdateEmployeeHandler updateEmployeeHandler = new UpdateEmployeeHandler();
        RemoveEmployeeHandler removeEmployeeHandler = new RemoveEmployeeHandler();

        // expense handlers
        CreateExpenseHandler createExpenseHandler = new CreateExpenseHandler();
        GetSpecificExpensesHandler getSpecificExpensesHandler = new GetSpecificExpensesHandler();
        GetExpenseByIdHandler getExpenseByIdHandler = new GetExpenseByIdHandler();
        UpdateExpenseByIdHandler updateExpenseByIdHandler = new UpdateExpenseByIdHandler();
        UpdateExpenseStatusHandler updateExpenseStatusHandler = new UpdateExpenseStatusHandler();
        DeleteExpenseByIdHandler deleteExpenseByIdHandler = new DeleteExpenseByIdHandler();


        // employee routes
        app.post("/employees", createEmployeeHandler);
        app.get("/employees", getAllEmployeeHandler);
        app.get("/employees/{idNum}", getEmployeeByIdHandler);
        app.put("/employees/{idNum}", updateEmployeeHandler);
        app.delete("/employees/{idNum}", removeEmployeeHandler);

        // expense routes
        app.post("/expenses", createExpenseHandler);
        app.get("/expenses", getSpecificExpensesHandler);
        app.get("/expenses/{idNum}", getExpenseByIdHandler);
        //app.put("/expenses/{idNum}", null);
        //app.patch("/expenses/{idNum}/{newStatus}", null);
        //app.delete("/expenses/{idNum}", null);

        app.start();
    }
}
