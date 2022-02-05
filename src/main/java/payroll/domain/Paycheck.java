package payroll.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author zhangyunlong
 */
public class Paycheck {
    private LocalDate itsPayPeriodStartDate;
    private LocalDate itsPayPeriodEndDate;
    private double itsGrossPay;
    private double itsDeductions;
    private double itsNetPay;
    private Map<String, String> itsFields = new HashMap<>();

    public Paycheck(LocalDate payPeriodStartDate, LocalDate payDate) {
        this.itsPayPeriodStartDate = payPeriodStartDate;
        this.itsPayPeriodEndDate = payDate;
    }

    public void setGrossPay(double grossPay) {
        this.itsGrossPay = grossPay;
    }

    public void setDeductions(double deductions) {
        this.itsDeductions = deductions;
    }

    public void setNetPay(double netPay) {
        this.itsNetPay = netPay;
    }

    public void setField(String field, String value) {
        itsFields.put(field, value);
    }

    public LocalDate getPayPeriodEndDate() {
        return itsPayPeriodEndDate;
    }

    public double getGrossPay() {
        return itsGrossPay;
    }

    public double getDeductions() {
        return itsDeductions;
    }

    public double getNetPay() {
        return itsNetPay;
    }

    public String getField(String field) {
        return itsFields.get(field);
    }

    public LocalDate getPayPeriodStartDate() {
        return itsPayPeriodStartDate;
    }
}
