package dev.wu.handlers.employee;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetEmployeeByIdHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
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
    }
}
