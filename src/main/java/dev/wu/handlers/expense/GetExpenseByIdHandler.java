package dev.wu.handlers.expense;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.Expenses;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetExpenseByIdHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        int idNum = Integer.parseInt(ctx.pathParam(("idNum")));

        if (App.expenseService.getAllExpenses().containsKey(idNum)) {
            Expenses expense = App.expenseService.getExpenseById(idNum);
            Gson gson = new Gson();
            String json = gson.toJson(expense);
            ctx.result(json);
            return;
        }

        ctx.status(404);
        ctx.result("Couldn't find the expense of id " + idNum);
    }
}
