package dev.wu.entities;

public class Expenses {

    private int issuerId;

    private int expenseId;

    private double expenseAmount;

    private TypeOfExpense typeOfExpense;

    private StatusOfExpense status;

    public Expenses(){

    }

    public Expenses(int issuerId, int expenseId, double expenseAmount, TypeOfExpense typeOfExpense) {
        this.issuerId = issuerId;
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
        this.typeOfExpense = typeOfExpense;
        this.status = StatusOfExpense.PENDING;
    }

    public int getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(int issuerId) {
        this.issuerId = issuerId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public TypeOfExpense getTypeOfExpense() {
        return typeOfExpense;
    }

    public void setTypeOfExpense(TypeOfExpense typeOfExpense) {
        this.typeOfExpense = typeOfExpense;
    }

    public StatusOfExpense getStatus() {
        return status;
    }

    public void setStatus(StatusOfExpense status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "issuerId=" + issuerId +
                ", expenseId=" + expenseId +
                ", expenseAmount=" + expenseAmount +
                ", typeOfExpense=" + typeOfExpense +
                ", status=" + status +
                '}';
    }
}
