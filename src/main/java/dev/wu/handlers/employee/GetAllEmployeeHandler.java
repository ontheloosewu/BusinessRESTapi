package dev.wu.handlers.employee;

import com.google.gson.Gson;
import dev.wu.app.App;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetAllEmployeeHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(App.employeeService.getAllEmployees().values());
        ctx.result(json);
    }
}
