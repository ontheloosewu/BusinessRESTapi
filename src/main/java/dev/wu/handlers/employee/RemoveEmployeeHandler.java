package dev.wu.handlers.employee;

import dev.wu.app.App;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class RemoveEmployeeHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        int idNum = Integer.parseInt(ctx.pathParam("idNum"));

        if (App.employeeService.getEmployeeById(idNum) != null) {
            boolean isEmployeeRemoved = App.employeeService.employeeRemoved(idNum);
            ctx.result(String.valueOf(isEmployeeRemoved));
            return;
        }

        ctx.status(404);
        ctx.result("Couldn't find the employee to delete");
    }
}
