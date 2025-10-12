package expense;

import java.time.LocalDate;

public class Expense {
    private long id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    private String description;
    private String date;
    private int month;
    private float amount;

    public Expense(long id, String description, float amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = LocalDate.now().toString();
        this.month = generateMonth();
    }
    public long getId() {
        return id;
    }
    public int getMonth() {
        return month;
    }
    public String getDate() {
        return date;
    }
    private int generateMonth() throws IllegalArgumentException {
        if(this.date.length() != 10) {
            throw new IllegalArgumentException("Invalid date");
        }
        String month = this.date.substring(5, 7);
        return Integer.parseInt(month);
    }

}
