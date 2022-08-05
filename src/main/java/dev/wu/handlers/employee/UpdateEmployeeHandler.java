package dev.wu.handlers.employee;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class UpdateEmployeeHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
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
    }
}
