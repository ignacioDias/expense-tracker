import utils.ExpenseTracker;
import utils.JSONMethods;

public class Main {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = JSONMethods.loadState();
        if(args.length == 0){
            throw new IllegalArgumentException("Illegal number of arguments");
        }
        switch(args[0]) {
            case "add":
                if(args.length != 5)
                    throw new IllegalArgumentException("Illegal number of arguments");
                expenseTracker.addExpense(args[2], Float.parseFloat(args[4]));
                JSONMethods.saveState(expenseTracker);
                break;
            case "update":
                if (args.length < 3)
                    throw new IllegalArgumentException("Illegal number of arguments");
                long id = Long.parseLong(args[2]);
                String description = null;
                Float amount = null;

                // si hay 4 argumentos, puede ser desc o monto
                if (args.length == 4) {
                    try {
                        amount = Float.parseFloat(args[3]);
                    } catch (NumberFormatException e) {
                        // si falla, es descripción
                        description = args[3];
                    }
                }
                else if (args.length == 5) {
                    description = args[3];
                    amount = Float.parseFloat(args[4]);
                }
                boolean updated = expenseTracker.updateExpense(id, description, amount);
                if (updated) {
                    JSONMethods.saveState(expenseTracker);
                    System.out.println("Expense " + id + " updated successfully.");
                } else {
                    System.out.println("Expense not found or no fields updated.");
                }
                break;
            case "delete":
                if(args.length != 3)
                    throw new IllegalArgumentException("Illegal number of arguments");
                expenseTracker.deleteExpense(Long.parseLong(args[2]));
                JSONMethods.saveState(expenseTracker);
                break;
            case "list":
                if(args.length != 1)
                    throw new IllegalArgumentException("Illegal number of arguments");
                expenseTracker.listAllExpenses();
                break;
            case "summary":
                if(args.length == 1)
                    System.out.println("Total expenses: " + expenseTracker.getAllAmounts());
                else if(args.length == 3)
                    System.out.println("Total expenses for month" + args[2] + ": " + expenseTracker.getAllAmountsFromMonth(Integer.parseInt(args[2])));
                break;
            default:
                throw new IllegalArgumentException("Illegal argument " + args[0]);
        }
    }
}
/*
Users can update an expense.
 */