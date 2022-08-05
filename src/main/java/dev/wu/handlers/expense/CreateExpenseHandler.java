package dev.wu.handlers.expense;

import com.google.gson.Gson;
import dev.wu.app.App;
import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateExpenseHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        String body = ctx.body();
        Gson gson = new Gson();
        Expense expense = gson.fromJson(body, Expense.class);
        expense.setStatus(StatusOfExpense.PENDING);
        Expense newExpense = App.expenseService.newValidExpense(expense);
        String json = gson.toJson(newExpense);

        ctx.status(201);
        ctx.result(json);
    }
}
