package dev.wu.handlers.expense;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class UpdateExpenseByIdHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        int idNum = Integer.parseInt(ctx.pathParam(("idNum")));

        if (App.expenseService.getExpenseById(idNum) != null) {
            String expenseJson = ctx.body();
            Gson gson = new Gson();
            Expense newExpense = gson.fromJson(expenseJson, Expense.class);
            newExpense.setStatus(App.expenseService.getExpenseById(idNum).getStatus());
            App.expenseService.updateExpense(newExpense, StatusOfExpense.PENDING);

            String json = gson.toJson(newExpense);
            ctx.result(json);
            return;
        }

        ctx.status(404);
        ctx.result("Couldn't find the expense of id: " + idNum + " to update");
    }
}
