package payroll.implementation;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class ServiceCharge {
    private LocalDate itsDate;
    private double itsCharge;

    public ServiceCharge(LocalDate date, double charge) {
        this.itsDate = date;
        this.itsCharge = charge;
    }

    public LocalDate getDate() {
        return itsDate;
    }

    public double getCharge() {
        return itsCharge;
    }
}
