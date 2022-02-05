package transaction.implementation;

import payroll.domain.Employee;
import payroll.domain.PaymentClassification;
import payroll.database.PayrollDataBase;
import payroll.implementation.HourlyClassification;
import payroll.implementation.TimeCard;
import transaction.application.Transaction;

import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * @author zhangyunlong
 */
public class TimeCardTransaction implements Transaction {
    private int itsEmpId;
    private LocalDate itsWorkDate;
    private double itsHours;
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public TimeCardTransaction(int empId, LocalDate workDate, double hours) {
        this.itsEmpId = empId;
        this.itsWorkDate = workDate;
        this.itsHours = hours;
    }

    @Override
    public void execute() {
        Employee e = payrollDataBase.getEmployee(itsEmpId);
        if (e != null) {
            PaymentClassification pc = e.getClassification();
            try {
                HourlyClassification hc = (HourlyClassification) pc;
                hc.addTimeCard(new TimeCard(itsWorkDate, itsHours));
            } catch (Exception ex) {
                throw new UnsupportedOperationException("Tried to add time card to non-hourly employee");
            }
        } else {
            throw new NoSuchElementException("No such employee.");
        }
    }
}
