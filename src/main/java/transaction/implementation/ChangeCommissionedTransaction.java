package transaction.implementation;

import payroll.implementation.BiweeklySchedule;
import payroll.implementation.CommissionedClassification;
import transaction.ChangeClassificationTransaction;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentSchedule;

/**
 * @author zhangyunlong
 */
public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {
    private double itsSalary;
    private double itsCommissionRate;

    public ChangeCommissionedTransaction(int empId, double itsSalary, double itsCommissionRate) {
        super(empId);
        this.itsSalary = itsSalary;
        this.itsCommissionRate = itsCommissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new CommissionedClassification(itsSalary, itsCommissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
