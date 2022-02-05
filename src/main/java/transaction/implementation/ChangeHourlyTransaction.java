package transaction.implementation;

import payroll.implementation.HourlyClassification;
import payroll.implementation.WeeklySchedule;
import transaction.ChangeClassificationTransaction;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentSchedule;

/**
 * @author zhangyunlong
 */
public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private double itsHourlyRate;

    public ChangeHourlyTransaction(int empId, double hourlyRate) {
        super(empId);
        this.itsHourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new HourlyClassification(itsHourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
