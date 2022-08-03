package dev.wu.handlers.employee;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateEmployeeHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String body = ctx.body();
        Gson gson = new Gson();
        Employee employee = gson.fromJson(body, Employee.class);
        Employee newEmployee = App.employeeService.newValidEmployee(employee);
        String json = gson.toJson(newEmployee);

        ctx.status(201);
        ctx.result(json);
    }
}
