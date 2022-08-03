package dev.wu.handlers.expense;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.StatusOfExpense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetSpecificExpensesHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Gson gson = new Gson();
        String json;
        if(ctx.queryParam("status") == null){
            json = gson.toJson(App.expenseService.getAllExpenses().values());
        } else {
            StatusOfExpense status = StatusOfExpense.valueOf(ctx.queryParam("status").toUpperCase());
            json = gson.toJson(App.expenseService.getSpecificExpenses(status).values());
        }
        ctx.result(json);
    }
}
