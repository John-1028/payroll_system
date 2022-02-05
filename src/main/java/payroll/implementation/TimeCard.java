package payroll.implementation;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class TimeCard {
    private LocalDate itsDate;
    private double itsHours;

    public TimeCard(LocalDate date, double hours) {
        this.itsDate = date;
        this.itsHours = hours;
    }

    public LocalDate getDate() {
        return itsDate;
    }

    public double getHours() {
        return itsHours;
    }
}
