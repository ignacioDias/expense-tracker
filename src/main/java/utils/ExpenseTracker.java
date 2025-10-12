package utils;

import expense.Expense;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ExpenseTracker {

    private HashMap<Long, Expense> expenses;
    private AtomicLong counter;

    public ExpenseTracker(int startingCounter) {
        expenses = new HashMap<>();
        counter = new AtomicLong(startingCounter);
    }
    public void addExpense(String description, float amount)  {
        if(amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        long id = counter.incrementAndGet();
        Expense expense = new Expense(id, description, amount);
        expenses.put(expense.getId(), expense);
        System.out.println("Expense added successfully (ID: " + expense.getId() + ")");
    }
    public boolean deleteExpense(long id) {
        return expenses.remove(id) != null;
    }
    public boolean updateExpense(long id, String description, Float amount) {
        Expense expense = expenses.get(id);
        if (expense == null) return false;
        boolean updated = false;
        if (description != null) {
            expense.setDescription(description);
            updated = true;
        }
        if (amount != null && amount >= 0) {
            expense.setAmount(amount);
            updated = true;
        }
        return updated;
    }

    public float getAllAmounts() {
        float total = 0;
        for(Expense expense : expenses.values()) {
            total += expense.getAmount();
        }
        return total;
    }
    public float getAllAmountsFromMonth(int month) {
        float total = 0;
        int currentYear = LocalDate.now().getYear();
        for(Expense expense : expenses.values()) {
            int expenseYear = Integer.parseInt(expense.getDate().substring(0, 4));
            if(month == expense.getMonth() && currentYear == expenseYear) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public void listAllExpenses() {
        System.out.println("# \t ID \t Date \t Description \t Amount");
        for(Expense expense : expenses.values()) {
            System.out.println(expense.getId() + "\t" +  expense.getDate() + "\t" + expense.getDescription() + "\t" + expense.getAmount());
        }
    }
}
