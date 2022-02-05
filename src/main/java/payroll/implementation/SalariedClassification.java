package payroll.implementation;

import payroll.domain.Paycheck;
import payroll.domain.PaymentClassification;

/**
 * @author zhangyunlong
 */
public class SalariedClassification implements PaymentClassification {
    private double itsSalary;

    public SalariedClassification(double salary) {
        this.itsSalary = salary;
    }

    @Override
    public double calculatePay(Paycheck pc) {
        return itsSalary;
    }

    public double getSalary() {
        return itsSalary;
    }
}
