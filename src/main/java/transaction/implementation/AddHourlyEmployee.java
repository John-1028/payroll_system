package transaction.implementation;

import payroll.implementation.HourlyClassification;
import payroll.implementation.WeeklySchedule;
import transaction.AddEmployeeTransaction;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentSchedule;

/**
 * @author zhangyunlong
 */
public class AddHourlyEmployee extends AddEmployeeTransaction {
    private double hourlyRate;

    public AddHourlyEmployee(int empId, String name, String address, double hourlyRate) {
        super(empId, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
