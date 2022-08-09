package dev.wu.app;

import com.google.gson.Gson;
import dev.wu.daos.EmployeeDAOPostgres;
import dev.wu.daos.ExpenseDAOPostgres;
import dev.wu.entities.Employee;
import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import dev.wu.services.EmployeeService;
import dev.wu.services.EmployeeServiceImpl;
import dev.wu.services.ExpenseService;
import dev.wu.services.ExpenseServiceImpl;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgres());
    public static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOPostgres());

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // employee handlers
//        CreateEmployeeHandler createEmployeeHandler = new CreateEmployeeHandler();
//        GetAllEmployeeHandler getAllEmployeeHandler = new GetAllEmployeeHandler();
//        GetEmployeeByIdHandler getEmployeeByIdHandler = new GetEmployeeByIdHandler();
//        UpdateEmployeeHandler updateEmployeeHandler = new UpdateEmployeeHandler();
//        RemoveEmployeeHandler removeEmployeeHandler = new RemoveEmployeeHandler();

        // expense handlers
//        CreateExpenseHandler createExpenseHandler = new CreateExpenseHandler();
//        GetSpecificExpensesHandler getSpecificExpensesHandler = new GetSpecificExpensesHandler();
//        GetExpenseByIdHandler getExpenseByIdHandler = new GetExpenseByIdHandler();
//        UpdateExpenseByIdHandler updateExpenseByIdHandler = new UpdateExpenseByIdHandler();
//        UpdateExpenseStatusHandler updateExpenseStatusHandler = new UpdateExpenseStatusHandler();
//        DeleteExpenseByIdHandler deleteExpenseHandler = new DeleteExpenseByIdHandler();


        // employee routes
        Handler createEmployeeHandler = ctx -> {
            String body = ctx.body();
            Gson gson = new Gson();
            Employee employee = gson.fromJson(body, Employee.class);
            Employee newEmployee = App.employeeService.newValidEmployee(employee);
            String json = gson.toJson(newEmployee);

            ctx.status(201);
            ctx.result(json);
        };

        Handler getAllEmployeeHandler = ctx -> {
            Gson gson = new Gson();
            String json = gson.toJson(App.employeeService.getAllEmployees());
            ctx.result(json);
        };

        Handler getEmployeeByIdHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));

            if (App.employeeService.getEmployeeById(idNum) != null) {
                Employee employee = App.employeeService.getEmployeeById(idNum);
                Gson gson = new Gson();
                String json = gson.toJson(employee);
                ctx.result(json);
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the employee");
        };

        Handler updateEmployeeHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam("idNum"));

            if (App.employeeService.getEmployeeById(idNum) != null) {
                String employeeJson = ctx.body();
                Gson gson = new Gson();
                Employee employee = gson.fromJson(employeeJson, Employee.class);
                Employee updatedEmployee = App.employeeService.updatedEmployee(employee);

                String json = gson.toJson(updatedEmployee);
                ctx.result(json);
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the employee to update");
        };

        Handler removeEmployeeHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam("idNum"));

            if (App.employeeService.getEmployeeById(idNum) != null) {
                boolean isEmployeeRemoved = App.employeeService.employeeRemoved(idNum);
                ctx.result(String.valueOf(isEmployeeRemoved));
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the employee to delete");
        };

        app.post("/employees", createEmployeeHandler);
        app.get("/employees", getAllEmployeeHandler);
        app.get("/employees/{idNum}", getEmployeeByIdHandler);
        app.put("/employees/{idNum}", updateEmployeeHandler);
        app.delete("/employees/{idNum}", removeEmployeeHandler);

        // expense routes
        Handler createExpenseHandler = ctx -> {
            String body = ctx.body();
            Gson gson = new Gson();
            String json;
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setStatus(StatusOfExpense.PENDING);
            if(App.employeeService.getEmployeeById(expense.getIssuerId()) != null) {
                Expense newExpense = App.expenseService.newValidExpense(expense);
                json = gson.toJson(newExpense);
            } else {
                ctx.status(404);
                ctx.result("Expense not created, cannot find employee with id of " + expense.getIssuerId());
                return;
            }

            ctx.status(201);
            ctx.result(json);
        };

        Handler getSpecificExpensesHandler = ctx -> {
            Gson gson = new Gson();
            String json;
            if (ctx.queryParam("status") == null) {
                json = gson.toJson(App.expenseService.getAllExpenses());
            } else {
                StatusOfExpense status = StatusOfExpense.valueOf(ctx.queryParam("status").toUpperCase());
                json = gson.toJson(App.expenseService.getSpecificExpenses(status));
            }
            ctx.result(json);
        };

        Handler getExpenseByIdHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));

            if (App.expenseService.getExpenseById(idNum) != null) {
                Expense expense = App.expenseService.getExpenseById(idNum);
                Gson gson = new Gson();
                String json = gson.toJson(expense);
                ctx.result(json);
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the expense of id: " + idNum);
        };

        Handler updateExpenseByIdHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));
            Expense expense = App.expenseService.getExpenseById(idNum);
            if (expense != null) {
                String expenseJson = ctx.body();
                Gson gson = new Gson();
                Expense newExpense = gson.fromJson(expenseJson, Expense.class);
                if(expense.getIssuerId() == newExpense.getIssuerId()){
                    newExpense.setStatus(App.expenseService.getExpenseById(idNum).getStatus());
                    App.expenseService.updateExpense(newExpense, StatusOfExpense.PENDING);

                    String json = gson.toJson(newExpense);
                    ctx.result(json);
                } else {
                    ctx.status(404);
                    ctx.result("Couldn't find the employee tied to this expense");
                }
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the expense of id: " + idNum + " to update");
        };

        Handler updateExpenseStatusHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));
            StatusOfExpense status = StatusOfExpense.valueOf(ctx.pathParam("newStatus").toUpperCase());

            if (App.expenseService.getExpenseById(idNum) != null) {
                if (status == StatusOfExpense.APPROVED || status == StatusOfExpense.DENIED) {
                    Expense expenseToUpdate = App.expenseService.getExpenseById(idNum);
                    App.expenseService.updateExpense(expenseToUpdate, status);
                    ctx.result(expenseToUpdate.getStatus().toString());
                } else {
                    ctx.result("Please type in approved or denied");
                }
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the expense of id: " + idNum + " to approve/deny");
        };

        Handler deleteExpenseHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));

            if (App.expenseService.getExpenseById(idNum) != null) {
                if (App.expenseService.getExpenseById(idNum).getStatus() == StatusOfExpense.APPROVED
                        || App.expenseService.getExpenseById(idNum).getStatus() == StatusOfExpense.DENIED) {
                    ctx.status(422);
                    ctx.result("Can't delete an expense that's been approved/denied");
                } else {
                    boolean isExpenseRemoved = App.expenseService.expenseDeleted(idNum);
                    ctx.result(String.valueOf(isExpenseRemoved));
                }
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the expense of id: " + idNum + " to delete");
        };

        app.post("/expenses", createExpenseHandler);
        app.get("/expenses", getSpecificExpensesHandler);
        app.get("/expenses/{idNum}", getExpenseByIdHandler);
        app.put("/expenses/{idNum}", updateExpenseByIdHandler);
        app.patch("/expenses/{idNum}/{newStatus}", updateExpenseStatusHandler);
        app.delete("/expenses/{idNum}", deleteExpenseHandler);

        // nested routes
        Handler getExpensesByEmployeeIdHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));
            Employee employee = App.employeeService.getEmployeeById(idNum);
            if (employee != null) {
                List<Expense> expenses = new ArrayList();
                for (Expense e : expenseService.getAllExpenses()){
                    if (e.getIssuerId() == employee.getEmployeeId()){
                        expenses.add(e);
                    }
                }
                Gson gson = new Gson();
                String json = gson.toJson(expenses);
                ctx.result(json);
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the employee");
        };

        Handler createExpenseWithEmployeeIdHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam(("idNum")));
            Employee employee = App.employeeService.getEmployeeById(idNum);
            if (employee != null) {
                String body = ctx.body();
                Gson gson = new Gson();
                Expense expense = gson.fromJson(body, Expense.class);
                expense.setStatus(StatusOfExpense.PENDING);
                Expense newExpense = App.expenseService.newValidExpense(expense);
                String json = gson.toJson(newExpense);

                ctx.status(201);
                ctx.result(json);
                return;
            }

            ctx.status(404);
            ctx.result("Couldn't find the employee");
        };

        app.get("/employees/{idNum}/expenses", getExpensesByEmployeeIdHandler);
        app.post("/employees/{idNum}/expenses", createExpenseWithEmployeeIdHandler);

        app.start();
    }
}
