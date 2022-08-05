package dev.wu.handlers.expense;

import dev.wu.app.App;
import dev.wu.entities.Expense;
import dev.wu.entities.StatusOfExpense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class UpdateExpenseStatusHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
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
    }
}
