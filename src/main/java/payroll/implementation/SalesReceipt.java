package payroll.implementation;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class SalesReceipt {
    private LocalDate date;
    private double amount;

    public SalesReceipt(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
