package transaction.implementation;

import payroll.implementation.MonthlySchedule;
import payroll.implementation.SalariedClassification;
import transaction.AddEmployeeTransaction;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentSchedule;

/**
 * @author zhangyunlong
 */
public class AddSalariedEmployee extends AddEmployeeTransaction {
    private double itsSalary;

    public AddSalariedEmployee(int epmId, String name, String address, double salary) {
        super(epmId, name, address);
        this.itsSalary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(itsSalary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
