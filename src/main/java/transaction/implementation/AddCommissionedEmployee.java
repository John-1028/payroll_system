package transaction.implementation;

import payroll.implementation.BiweeklySchedule;
import payroll.implementation.CommissionedClassification;
import transaction.AddEmployeeTransaction;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentSchedule;

/**
 * @author zhangyunlong
 */
public class AddCommissionedEmployee extends AddEmployeeTransaction {
    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(int empId, String name,
                                   String address, double salary, double commissionRate) {
        super(empId, name, address);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
