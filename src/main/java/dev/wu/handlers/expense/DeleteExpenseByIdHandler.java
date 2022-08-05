package dev.wu.handlers.expense;

import dev.wu.app.App;
import dev.wu.entities.StatusOfExpense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class DeleteExpenseByIdHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        int idNum = Integer.parseInt(ctx.pathParam(("idNum")));

        if (App.expenseService.getExpenseById(idNum) != null) {
            if (App.expenseService.getExpenseById(idNum).getStatus() == StatusOfExpense.APPROVED
                    || App.expenseService.getExpenseById(idNum).getStatus() == StatusOfExpense.DENIED) {
                ctx.status(422);
                ctx.result("Can't delete an expense that's been approved/denied");
            } else {
                boolean isExpenseRemoved = App.expenseService.expenseDeleted(idNum);
                ctx.result(String.valueOf(isExpenseRemoved));
            }
            return;
        }

        ctx.status(404);
        ctx.result("Couldn't find the expense of id: " + idNum + " to delete");
    }
}
